package org.rec.planets.jupiter.action.network.client.lifecycle;

import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * http客户端准备拦截器,此拦截器尝试从全局缓存中获取客户端
 * 
 * @author rec
 * 
 */
public class GlobalCachedClientInterceptor extends AbstractClientPrepareInterceptor {
	private GlobalClientCache globalClientCache;

	@Override
	protected void setClient(ActionContext context) {
		Client client = (Client) contextReader
				.read(context, clientKey);
		if (client == null) {
			client = globalClientCache.getClient(context, clientFactory);
			contextWriter.write(context, clientKey, client);
		}

	}

	public void setGlobalClientCache(GlobalClientCache globalClientCache) {
		this.globalClientCache = globalClientCache;
	}
}
