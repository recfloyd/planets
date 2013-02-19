package org.rec.planets.jupiter.bean;

import java.util.Map;

import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.mercury.domain.AbstractBean;

public class CrawlRule extends AbstractBean {
	private Short websiteId;
	private Long version;
	private Map<String, ?> websiteProperties;
	private CrawlProcessor crawlProcessor;

	public CrawlRule(Short websiteId, Long version,
			Map<String, ?> websiteProperties, CrawlProcessor crawlProcessor) {
		this.websiteId = websiteId;
		this.version = version;
		this.websiteProperties = websiteProperties;
		this.crawlProcessor = crawlProcessor;
	}

	public Short getWebsiteId() {
		return websiteId;
	}

	public Long getVersion() {
		return version;
	}

	public Map<String, ?> getWebsiteProperties() {
		return websiteProperties;
	}

	public CrawlProcessor getCrawlProcessor() {
		return crawlProcessor;
	}
}
