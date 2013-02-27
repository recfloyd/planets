package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.bean.CrawlContextConstants;
import org.rec.planets.jupiter.processor.network.client.Client;

/**
 * http客户端准备过滤器,每个crawlContext里只准备一份客户端
 * 
 * @author rec
 * 
 */
public class ContextCachedClientFilter extends AbstractClientPrepareFilter {

	@Override
	protected void setClient(CrawlContext crawlContext) {
		Client client = (Client) crawlContext.getContext().get(
				CrawlContextConstants.KEY_CLIENT);
		if (client == null) {
			client = clientFactory.getClient(crawlContext);
			crawlContextAccessor.set(crawlContext, client);
		}
	}
}
