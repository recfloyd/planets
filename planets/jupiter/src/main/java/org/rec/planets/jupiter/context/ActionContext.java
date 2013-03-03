package org.rec.planets.jupiter.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.rec.planets.jupiter.slot.ThreadSafeCurrentJob;
import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;

/**
 * 处理器上下文,保存了对于一个url进行处理的所有中间和最后结果
 * 
 * @author rec
 * 
 */
public class ActionContext extends AbstractBean {
	private CrawlURL crawlURL;
	private Map<String, Object> websiteProperties;
	private ConcurrentMap<String, Object> slotContext;
	private ThreadSafeCurrentJob currentJob;
	private Map<String, Object> urlcontext;

	public ActionContext(CrawlURL crawlURL,
			Map<String, Object> websiteProperties,
			ConcurrentMap<String, Object> slotContext, ThreadSafeCurrentJob currentJob) {
		this.crawlURL = crawlURL;
		this.websiteProperties = websiteProperties;
		this.slotContext = slotContext;
		this.currentJob = currentJob;
		this.urlcontext = new ConcurrentHashMap<String, Object>();
	}

	public CrawlURL getCrawlURL() {
		return crawlURL;
	}

	public Map<String, Object> getWebsiteProperties() {
		return websiteProperties;
	}

	public ConcurrentMap<String, Object> getSlotContext() {
		return slotContext;
	}

	public ThreadSafeCurrentJob getCurrentJob() {
		return currentJob;
	}

	public Map<String, Object> getUrlcontext() {
		return urlcontext;
	}
}
