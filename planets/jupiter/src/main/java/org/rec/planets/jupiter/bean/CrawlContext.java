package org.rec.planets.jupiter.bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.domain.Job;

public class CrawlContext extends AbstractBean {
	private Map<String, Object> websiteProperties;
	private Job job;
	private CrawlURL crawlURL;
	private Map<String, Object> context;

	public CrawlContext(Map<String, Object> websiteProperties, Job job,
			CrawlURL crawlURL) {
		this.websiteProperties = websiteProperties;
		this.job = job;
		this.crawlURL = crawlURL;
		this.context = new ConcurrentHashMap<String, Object>();
	}

	public Map<String, Object> getWebsiteProperties() {
		return websiteProperties;
	}

	public CrawlURL getCrawlURL() {
		return crawlURL;
	}

	public Map<String, Object> getContext() {
		return context;
	}

	public Job getJob() {
		return job;
	}
}
