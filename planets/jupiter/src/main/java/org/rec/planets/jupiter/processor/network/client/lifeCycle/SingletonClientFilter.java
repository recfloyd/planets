package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.bean.CrawlContextConstants;
import org.rec.planets.jupiter.processor.network.client.Client;

/**
 * http客户端准备过滤器,每次都创建新的客户端
 * 
 * @author rec
 * 
 */
public class SingletonClientFilter extends AbstractClientPrepareFilter {

	@Override
	protected void setClient(CrawlContext crawlContext) {
		Client client = clientFactory.getClient(crawlContext);
		crawlContextAccessor.set(crawlContext,
				CrawlContextConstants.KEY_CLIENT, client);
	}
}
