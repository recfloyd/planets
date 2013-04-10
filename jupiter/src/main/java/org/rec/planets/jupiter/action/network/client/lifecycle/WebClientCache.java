package org.rec.planets.jupiter.action.network.client.lifecycle;

import java.util.concurrent.ConcurrentHashMap;

import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.ClientFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.beans.factory.DisposableBean;

/**
 * 以websiteId为key的客户端缓存容器
 * 
 * @author rec
 * 
 */
public final class WebClientCache implements ClientCache, DisposableBean {
	private ConcurrentHashMap<Short, Client> cacheMap = new ConcurrentHashMap<Short, Client>();

	public Client getClient(ActionContext context, ClientFactory clientFactory) {
		Short websiteId = context.getCrawlURL().getWebsiteId();
		Client client = cacheMap.get(websiteId);
		if (client == null) {
			cacheMap.putIfAbsent(websiteId, clientFactory.getClient(context));
			client = cacheMap.get(websiteId);
		}
		return client;
	}

	@Override
	public void destroy() throws Exception {
		for (Client client : cacheMap.values()) {
			client.destroy();
		}
		cacheMap = null;
	}
}
