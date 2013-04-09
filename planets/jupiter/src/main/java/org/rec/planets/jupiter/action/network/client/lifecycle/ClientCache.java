package org.rec.planets.jupiter.action.network.client.lifecycle;

import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.ClientFactory;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 客户端缓存容器
 * 
 * @author rec
 * 
 */
public interface ClientCache {
	Client getClient(ActionContext actionContext, ClientFactory clientFactory);
}
