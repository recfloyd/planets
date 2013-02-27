package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.CrawlContextReader;
import org.rec.planets.jupiter.processor.accessor.CrawlContextWriter;
import org.rec.planets.jupiter.processor.filter.Filter;
import org.rec.planets.jupiter.processor.network.client.factory.ClientFactory;

/**
 * http客户端准备过滤器
 * 
 * @author rec
 * 
 */
public abstract class AbstractClientPrepareFilter implements Filter {
	protected ClientFactory clientFactory;
	protected CrawlContextReader crawlContextReader;
	protected CrawlContextWriter crawlContextWriter;

	protected String clientKey;

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

	public void setCrawlContextWriter(CrawlContextWriter crawlContextWriter) {
		this.crawlContextWriter = crawlContextWriter;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public void setCrawlContextReader(CrawlContextReader crawlContextReader) {
		this.crawlContextReader = crawlContextReader;
	}
}
