package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.bean.CrawlContextConstants;
import org.rec.planets.jupiter.processor.network.client.Client;

public class SingletonClientFilter extends AbstractClientPrepareFilter {

	@Override
	protected void setClient(CrawlContext crawlContext) {
		Client client = clientFactory.getClient(crawlContext);
		crawlContext.getContext().put(CrawlContextConstants.KEY_CLIENT, client);
	}
}
