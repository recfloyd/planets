package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.bean.CrawlContextConstants;
import org.rec.planets.jupiter.processor.network.client.Client;

/**
 * http客户端准备过滤器,此过滤器尝试从全局缓存中获取客户端
 * @author rec
 *
 */
public class GlobalCachedClientFilter extends AbstractClientPrepareFilter {
	private GlobalClientCache globalClientCache;

	@Override
	protected void setClient(CrawlContext crawlContext) {
		Client client = (Client) crawlContext.getContext().get(
				CrawlContextConstants.KEY_CLIENT);
		if (client == null) {
			client = globalClientCache.getClient(crawlContext, clientFactory);
			crawlContext.getContext().put(CrawlContextConstants.KEY_CLIENT,
					client);
		}

	}

	public void setGlobalClientCache(GlobalClientCache globalClientCache) {
		this.globalClientCache = globalClientCache;
	}

}
