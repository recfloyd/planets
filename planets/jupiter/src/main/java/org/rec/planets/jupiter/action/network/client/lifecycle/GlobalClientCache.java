package org.rec.planets.jupiter.action.network.client.lifecycle;

import java.util.concurrent.ConcurrentHashMap;

import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.factory.ClientFactory;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * http客户端全局缓存
 * @author rec
 *
 */
public final class GlobalClientCache {
	private ConcurrentHashMap<Short, Client> clientCache;

	public Client getClient(ActionContext crawlContext,
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
