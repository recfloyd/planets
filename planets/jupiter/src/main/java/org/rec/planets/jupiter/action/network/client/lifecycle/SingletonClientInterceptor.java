package org.rec.planets.jupiter.action.network.client.lifecycle;

import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * http客户端准备拦截器,每次都创建新的客户端
 * 
 * @author rec
 * 
 */
public class SingletonClientInterceptor extends AbstractClientPrepareInterceptor {
	@Override
	protected void setClient(ActionContext context) {
		Client client = clientFactory.getClient(context);
		contextWriter.write(context, clientKey, client);
	}
}
