package org.rec.planets.jupiter.action.network.client.lifecycle;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.interceptor.Interceptor;
import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.factory.ClientFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.AbstractReadWriteSupport;

/**
 * http客户端准备拦截器,每次都创建新的客户端,并负责其销毁工作
 * 
 * @author rec
 * 
 */
public class DestroiableClientInterceptor extends AbstractReadWriteSupport
		implements Interceptor {
	private ClientFactory clientFactory;

	@Override
	public void invoke(Action action, ActionContext context) throws Exception {
		Client client = clientFactory.getClient(context);
		writeResult(context, client);
		try {
			action.execute(context);
		} finally {
			client.destroy();
			removeObject(context);
		}
	}

	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

}
