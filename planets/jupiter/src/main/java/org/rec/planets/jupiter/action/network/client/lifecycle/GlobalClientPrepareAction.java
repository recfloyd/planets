package org.rec.planets.jupiter.action.network.client.lifecycle;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.ClientFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.AbstractReadWriteSupport;

/**
 * 全局客户端准备器
 * @author rec
 *
 */
public class GlobalClientPrepareAction extends AbstractReadWriteSupport
		implements Action {
	private GlobalClientCache globalClientCache;
	private ClientFactory clientFactory;

	@Override
	public void execute(ActionContext context) throws Exception {
		Client client = (Client) getSource(context);
		if (client == null) {
			client = globalClientCache.getClient(context, clientFactory);
			writeResult(context, client);
		}
	}

	public void setGlobalClientCache(GlobalClientCache globalClientCache) {
		this.globalClientCache = globalClientCache;
	}

	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
}
