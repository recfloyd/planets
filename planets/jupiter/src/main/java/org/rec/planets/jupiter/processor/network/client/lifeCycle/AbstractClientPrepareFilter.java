package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessable;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessor;
import org.rec.planets.jupiter.processor.filter.Filter;
import org.rec.planets.jupiter.processor.network.client.factory.ClientFactory;

/**
 * http客户端准备过滤器
 * 
 * @author rec
 * 
 */
public abstract class AbstractClientPrepareFilter implements Filter, CrawlContextAccessable {
	protected ClientFactory clientFactory;
	protected CrawlContextAccessor crawlContextAccessor;

	protected abstract void setClient(CrawlContext crawlContext);

	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void invoke(CrawlProcessor processor, CrawlContext crawlContext)
			throws Exception {
		this.setClient(crawlContext);
		processor.process(crawlContext);
	}

	@Override
	public void setCrawlContextAccessor(
			CrawlContextAccessor crawlContextAccessor) {
		this.crawlContextAccessor = crawlContextAccessor;
	}
}
