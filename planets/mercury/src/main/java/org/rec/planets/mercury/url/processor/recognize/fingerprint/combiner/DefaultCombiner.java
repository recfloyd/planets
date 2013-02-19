package org.rec.planets.mercury.url.processor.recognize.fingerprint.combiner;

import org.rec.planets.mercury.domain.CrawlURL;

public class DefaultCombiner implements Combiner {
	private String seperator = "#";

	@Override
	public String combine(CrawlURL crawlURL, String eigenvalue)
			throws Exception {
		StringBuilder buffer = new StringBuilder();
		buffer.append(crawlURL.getApplicationId()).append(seperator)
				.append(crawlURL.getWebsiteId()).append(seperator)
				.append(crawlURL.getPageType()).append(seperator)
				.append(eigenvalue);
		return buffer.toString();
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

}
