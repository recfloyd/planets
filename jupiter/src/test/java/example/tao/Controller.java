package example.tao;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.rec.planets.jupiter.system.command.RuleUpdateCommandHandler;
import org.rec.planets.mercury.communication.bean.CrawlEntity;
import org.rec.planets.mercury.communication.bean.CrawlPropagation;
import org.rec.planets.mercury.communication.bean.pack.JobPack;
import org.rec.planets.mercury.communication.bean.pack.ResultPack;
import org.rec.planets.mercury.communication.bean.snapshot.JobResultSnapshot;
import org.rec.planets.mercury.communication.service.FetchResultService;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.domain.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import au.com.bytecode.opencsv.CSVWriter;

public class Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(Controller.class);
	private FetchResultService fetchResultService;
	private Resource resource;
	private String[] rules;
	private Short websiteId;
	private File output;
	private AtomicLong jobCounter = new AtomicLong();

	public void run() throws Exception {
		InputStream is = resource.getInputStream();
		List<String> list = null;
		try {
			list = IOUtils.readLines(resource.getInputStream());
		} catch (Exception e) {
			IOUtils.closeQuietly(is);
			throw e;
		}

		logger.info("文件已经读取,共取到" + list.size() + "个url");

		Map<String, List<Map<String, Object>>> products = new ConcurrentHashMap<String, List<Map<String, Object>>>();
		JobPack jobPack = this.getJobPack(list);
		ResultPack resultPack = null;

		while (jobPack != null) {
			resultPack = fetchResultService.fetch(jobPack);
			collectResult(resultPack, products);
			jobPack = reformJobPack(resultPack);
		}

		FileUtils.deleteQuietly(output);
		FileUtils.touch(output);
		CSVWriter writer = new CSVWriter(new FileWriterWithEncoding(output,
				"UTF-8", true));
		String[] title = new String[] { "类别", "产品名称", "价格", "销量", "评论", "店铺",
				"URL" };
		writer.writeNext(title);

		for (Map.Entry<String, List<Map<String, Object>>> typeEntry : products
				.entrySet()) {
			for (Map<String, Object> product : typeEntry.getValue()) {
				String[] data = new String[7];
				data[0] = (String) product.get("type");
				data[1] = (String) product.get("name");
				data[2] = ((Double) product.get("price")).toString();
				data[3] = (String) product.get("sales");
				data[4] = (String) product.get("comments");
				data[4] = data[4] == null ? "" : data[4];
				data[5] = (String) product.get("shop");
				data[6] = (String) product.get("url");
				writer.writeNext(data);
			}
		}

		writer.flush();
		writer.close();
		System.out.println("完事了");
	}

	private JobPack pack(List<CrawlURL> urls, boolean updateRule) {
		Job job = new Job();
		job.setId(jobCounter.incrementAndGet());
		job.setWebsiteId(websiteId);
		job.setUrls(urls);

		JobPack result = new JobPack();
		result.setJobs(new ArrayList<Job>(1));
		result.getJobs().add(job);
		if (updateRule) {
			result.setCommand(new HashMap<String, Object>());
			Map<Short, String[]> updateRules = new HashMap<Short, String[]>();
			updateRules.put(websiteId, rules);
			result.getCommand().put(RuleUpdateCommandHandler.COMMAND_KEY,
					updateRules);
		}

		return result;
	}

	private JobPack getJobPack(List<String> list) {
		List<CrawlURL> urls = new ArrayList<CrawlURL>(list.size());
		CrawlURL tmp = null;
		String[] split = null;
		Map<String, Object> appParams = null;
		for (String string : list) {
			split = string.split(",");
			tmp = new CrawlURL();
			tmp.setUrl(split[0]);
			tmp.setWebsiteId(websiteId);
			appParams = new HashMap<String, Object>();
			appParams.put("todoPage", Integer.parseInt(split[1]));
			appParams.put("type", split[2]);
			tmp.setAppParams(appParams);
			urls.add(tmp);
		}

		return pack(urls, true);
	}

	private JobPack reformJobPack(ResultPack resultPack) {
		if (resultPack == null)
			return null;

		List<JobResultSnapshot> jobResultSnapshots = resultPack
				.getJobResultSnapshots();
		if (CollectionUtils.isEmpty(jobResultSnapshots))
			return null;

		List<CrawlURL> urls = new ArrayList<CrawlURL>();

		for (JobResultSnapshot jrs : jobResultSnapshots) {
			List<CrawlPropagation> propagation = jrs.getPropagations();
			if (propagation != null) {
				for (CrawlPropagation cp : propagation) {
					List<CrawlURL> children = cp.getChildren();
					if (children != null)
						urls.addAll(children);
				}
			}
		}

		if (urls.size() == 0)
			return null;
		else
			return pack(urls, false);
	}

	private void collectResult(ResultPack resultPack,
			Map<String, List<Map<String, Object>>> collection) {
		if (resultPack == null)
			return;

		List<JobResultSnapshot> jobResultSnapshots = resultPack
				.getJobResultSnapshots();
		if (CollectionUtils.isEmpty(jobResultSnapshots))
			return;

		for (JobResultSnapshot jrs : jobResultSnapshots) {
			List<CrawlEntity> entities = jrs.getEntities();
			if (entities != null)
				for (CrawlEntity ce : entities) {
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> content = (List<Map<String, Object>>) ce
							.getContent().get("product_list");
					for (Map<String, Object> product : content) {
						String type = (String) product.get("type");
						List<Map<String, Object>> products = collection
								.get(type);
						if (products == null) {
							products = new LinkedList<Map<String, Object>>();
							collection.put(type, products);
						}
						products.add(product);
					}
				}
		}
	}

	public void setFetchResultService(FetchResultService fetchResultService) {
		this.fetchResultService = fetchResultService;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setRules(String[] rules) {
		this.rules = rules;
	}

	public void setWebsiteId(Short websiteId) {
		this.websiteId = websiteId;
	}

	public void setOutput(File output) {
		this.output = output;
	}
}
