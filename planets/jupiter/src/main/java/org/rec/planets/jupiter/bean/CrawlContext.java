package org.rec.planets.jupiter.bean;

import java.util.Map;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;

/**
 * 抓取上下文,保存了对于一个url进行处理的所有中间和最后结果
 * 
 * @author rec
 * 
 */
public class CrawlContext extends AbstractBean {
	private CrawlURL crawlURL;
	private Map<String, Object> websiteProperties;
	private Map<String, Object> slotContext;
	private Map<String, Object> urlcontext;

	public CrawlContext(CrawlURL crawlURL,
			Map<String, Object> websiteProperties,
			Map<String, Object> slotContext, Map<String, Object> urlcontext) {
		this.crawlURL = crawlURL;
		this.websiteProperties = websiteProperties;
		this.slotContext = slotContext;
		this.urlcontext = urlcontext;
	}

	public CrawlURL getCrawlURL() {
		return crawlURL;
	}

	public Map<String, Object> getWebsiteProperties() {
		return websiteProperties;
	}

	public Map<String, Object> getSlotContext() {
		return slotContext;
	}

	public Map<String, Object> getUrlcontext() {
		return urlcontext;
	}
}
