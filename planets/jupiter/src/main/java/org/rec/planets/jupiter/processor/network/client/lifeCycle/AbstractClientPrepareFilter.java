package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.Filter;
import org.rec.planets.jupiter.processor.network.client.factory.ClientFactory;

public abstract class AbstractClientPrepareFilter implements Filter {
	protected ClientFactory clientFactory;

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
}
