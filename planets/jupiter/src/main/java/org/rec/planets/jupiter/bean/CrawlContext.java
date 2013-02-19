package org.rec.planets.jupiter.bean;

import java.util.Map;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.domain.Job;

public class CrawlContext extends AbstractBean {
	private Job job;
	private CrawlURL crawlURL;
	private Map<String, ?> context;

	public CrawlContext(Job job,CrawlURL crawlURL, Map<String, ?> context) {
		this.job=job;
		this.crawlURL = crawlURL;
		this.context = context;
	}

	public CrawlURL getCrawlURL() {
		return crawlURL;
	}

	public Map<String, ?> getContext() {
		return context;
	}

	public Job getJob() {
		return job;
	}
}
