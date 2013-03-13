package org.rec.planets.mercury.url.processor.recognize.website;

import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.parse.RegexUtil;
import org.rec.planets.mercury.url.processor.URLProcessor;

/**
 * 依靠一组网站的顶级域名对url进行识别
 * 
 * @author rec
 * 
 */
public class WebsiteRecognizor implements URLProcessor {
	/**
	 * 网站域名正则
	 */
	private Map<Short, Set<String>> hostRegexes;
	/**
	 * 如果网站id已经存在是否覆盖
	 */
	private boolean overwriten;

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		if (crawlURL.getWebsiteId() != null && !overwriten)
			return;
		String host = new URL(crawlURL.getUrl()).getHost();
		for (Map.Entry<Short, Set<String>> entry : hostRegexes.entrySet()) {
			for (String hostRegex : entry.getValue()) {
				if (RegexUtil.matches(host, hostRegex)) {
					crawlURL.setWebsiteId(entry.getKey());
					return;
				}
			}
		}

		throw new IllegalArgumentException("cannot find website for crawlURL: "
				+ crawlURL);
	}

	public void setHostRegexes(Map<Short, Set<String>> hostRegexes) {
		this.hostRegexes = hostRegexes;
	}

	public void setOverwriten(boolean overwriten) {
		this.overwriten = overwriten;
	}
}
