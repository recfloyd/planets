package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import java.util.concurrent.ConcurrentHashMap;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.network.client.Client;
import org.rec.planets.jupiter.processor.network.client.factory.ClientFactory;

/**
 * http客户端全局缓存
 * @author rec
 *
 */
public final class GlobalClientCache {
	private ConcurrentHashMap<Short, Client> clientCache;

	public Client getClient(CrawlContext crawlContext,
			ClientFactory clientFactory) {
		Short websiteId = crawlContext.getCrawlURL().getWebsiteId();
		Client client = clientCache.get(websiteId);
		if (client == null) {
			clientCache.putIfAbsent(websiteId,
					clientFactory.getClient(crawlContext));
			client = clientCache.get(websiteId);
		}
		return client;
	}
}
