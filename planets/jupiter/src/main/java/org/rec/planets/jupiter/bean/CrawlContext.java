package org.rec.planets.jupiter.bean;

import java.util.Map;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;

public class CrawlContext extends AbstractBean {
	private CrawlURL crawlURL;
	private Map<String, ?> context;

	public CrawlContext(CrawlURL crawlURL, Map<String, ?> context) {
		this.crawlURL = crawlURL;
		this.context = context;
	}

	public CrawlURL getCrawlURL() {
		return crawlURL;
	}

	public Map<String, ?> getContext() {
		return context;
	}
}
