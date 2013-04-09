package org.rec.planets.jupiter.action.network.client.lifecycle;

import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.ClientFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.beans.factory.DisposableBean;

public class GlobleClientCache implements ClientCache, DisposableBean {
	private volatile Client client;

	@Override
	public Client getClient(ActionContext context, ClientFactory clientFactory) {
		if (client == null) {
			synchronized (this) {
				if (client == null) {
					client = clientFactory.getClient(context);
				}
			}
		}
		return client;
	}

	@Override
	public void destroy() throws Exception {
		if (client != null) {
			client.destroy();
			client = null;
		}
	}
}
