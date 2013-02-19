package org.rec.planets.mercury.url.processor.recognize.website;

import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;

/**
 * 依靠一组网站的顶级域名对url进行识别
 * 
 * @author rec
 * 
 */
public class WebsiteRecognizor implements URLProcessor {
	/**
	 * 网站顶级域名映射,要求顶级域名都是小写的
	 */
	private Map<Short, Set<String>> topHostnames;
	/**
	 * 如果网站id已经存在是否覆盖
	 */
	private boolean overwriten;

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		if (crawlURL.getWebsiteId() != null && !overwriten)
			return;
		String host = new URL(crawlURL.getUrl()).getHost().toLowerCase();
		for (Map.Entry<Short, Set<String>> entry : topHostnames.entrySet()) {
			for (String topHost : entry.getValue()) {
				if (host.endsWith(topHost)) {
					crawlURL.setWebsiteId(entry.getKey());
					return;
				}
			}
		}

		throw new IllegalArgumentException("cannot find website for crawlURL: "
				+ crawlURL);
	}

	public void setTopHostnames(Map<Short, Set<String>> topHostnames) {
		this.topHostnames = topHostnames;
	}

	public void setOverwriten(boolean overwriten) {
		this.overwriten = overwriten;
	}
}
