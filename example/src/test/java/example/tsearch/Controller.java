package example.tsearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.rec.planets.jupiter.system.command.RuleUpdateCommandHandler;
import org.rec.planets.mercury.communication.bean.CrawlEntity;
import org.rec.planets.mercury.communication.bean.pack.JobPack;
import org.rec.planets.mercury.communication.bean.pack.ResultPack;
import org.rec.planets.mercury.communication.bean.snapshot.JobResultSnapshot;
import org.rec.planets.mercury.communication.service.FetchResultService;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.domain.Job;
import org.rec.planets.mercury.domain.constant.JobMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {
	private static final Logger logger = LoggerFactory
			.getLogger(Controller.class);
	private FetchResultService fetchResultService;
	private File input;
	private String[] rules;
	private Short websiteId;
	private File output;
	private AtomicLong jobCounter = new AtomicLong();
	private int lineStart;
	private int lineEnd;
	private int batchSize = 20;
	private boolean ruleSend = false;

	public void run() throws Exception {
		logger.info("read file " + input);
		List<String> lines = FileUtils.readLines(input);

		int start = lineStart == 0 ? 1 : lineStart;
		int end = lineEnd == 0 ? lines.size() : lineEnd;

		logger.info("read lines " + lines.size() + ", prcess from " + start
				+ " to " + end);

		FileUtils.deleteQuietly(output);

		List<CrawlURL> cache = new ArrayList<CrawlURL>(batchSize);

		int lineNumber = 0;
		CrawlURL tmp = null;
		Map<String, Object> appParams = null;
		for (String line : lines) {
			lineNumber++;
			if (lineNumber < start)
				continue;
			else if (lineNumber > end)
				break;

			if (StringUtils.isBlank(line))
				continue;

			tmp = new CrawlURL();
			tmp.setWebsiteId((short) 1);
			appParams = new HashMap<String, Object>();
			appParams.put("keyword", line);
			tmp.setAppParams(appParams);

			cache.add(tmp);
			if (cache.size() == batchSize) {
				process(cache, lineNumber);
				cache = new ArrayList<CrawlURL>(batchSize);
			}
		}
		if (cache.size() > 0)
			process(cache, lineNumber);

		logger.info("all done");
	}

	@SuppressWarnings("unchecked")
	private void process(List<CrawlURL> urls, int lineNumber) {
		Job job = new Job();
		job.setId(jobCounter.incrementAndGet());
		job.setWebsiteId(websiteId);
		job.setUrls(urls);
		job.setJobMode(JobMode.Active);

		JobPack jobPack = new JobPack();
		jobPack.setJobs(new ArrayList<Job>(1));
		jobPack.getJobs().add(job);

		if (!ruleSend) {
			jobPack.setCommand(new HashMap<String, Object>());
			Map<Short, String[]> updateRules = new HashMap<Short, String[]>();
			updateRules.put(websiteId, rules);
			jobPack.getCommand().put(RuleUpdateCommandHandler.COMMAND_KEY,
					updateRules);
			ruleSend = true;
		}

		ResultPack resultPack = fetchResultService.fetch(jobPack);
		List<JobResultSnapshot> jobResultSnapshots = resultPack
				.getJobResultSnapshots();
		if (CollectionUtils.isEmpty(jobResultSnapshots))
			return;

		List<CrawlEntity> entities = null;
		String keyword = null;
		List<String> firstPage = null;
		List<String> lastPage = null;
		StringBuilder sb = null;
		for (JobResultSnapshot jobResultSnapshot : jobResultSnapshots) {
			entities = jobResultSnapshot.getEntities();
			for (CrawlEntity crawlEntity : entities) {
				keyword = (String) crawlEntity.getContent().get("keyword");
				firstPage = (List<String>) crawlEntity.getContent().get(
						"firstPage");
				lastPage = (List<String>) crawlEntity.getContent().get(
						"lastPage");
				sb = new StringBuilder();
				sb.append("keyword:").append(keyword).append('\n');
				sb.append("\tfirstPage:\n");
				if (CollectionUtils.isNotEmpty(firstPage))
					for (String string : firstPage) {
						sb.append("\t\t").append(string).append('\n');
					}
				else
					sb.append("\t\tN/A\n");
				sb.append("\tlastPage:\n");
				if (CollectionUtils.isNotEmpty(lastPage))
					for (String string : lastPage) {
						sb.append("\t\t").append(string).append('\n');
					}
				else
					sb.append("\t\tN/A\n");
				sb.append("----------------\n");
				try {
					FileUtils.write(output, sb, true);
				} catch (IOException e) {
					logger.error("error when write data", e);
				}
			}
		}

		logger.info("done for job " + jobCounter.get() + ", and url line "
				+ lineNumber);
	}

	public void setFetchResultService(FetchResultService fetchResultService) {
		this.fetchResultService = fetchResultService;
	}

	public void setInput(File input) {
		this.input = input;
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

	public void setLineStart(int lineStart) {
		this.lineStart = lineStart;
	}

	public void setLineEnd(int lineEnd) {
		this.lineEnd = lineEnd;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
}
