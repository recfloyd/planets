package org.rec.planets.jupiter.action.network.client.lifecycle;

import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * http客户端准备拦截器,每个ActionContext里只准备一份客户端
 * 
 * @author rec
 * 
 */
public class ContextCachedClientInterceptor extends AbstractClientPrepareInterceptor {
	@Override
	protected void setClient(ActionContext context) {
		Client client = (Client) contextReader
				.read(context, clientKey);
		if (client == null) {
			client = clientFactory.getClient(context);
			contextWriter.write(context, clientKey, client);
		}
	}
}
