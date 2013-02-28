package org.rec.planets.jupiter.action.network.client.lifecycle;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.interceptor.Interceptor;
import org.rec.planets.jupiter.action.network.client.factory.ClientFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;
import org.rec.planets.jupiter.context.accessor.ContextWriter;

/**
 * http客户端准备拦截器
 * 
 * @author rec
 * 
 */
public abstract class AbstractClientPrepareInterceptor implements Interceptor {
	protected ClientFactory clientFactory;
	protected ContextReader contextReader;
	protected ContextWriter contextWriter;
	protected String clientKey;

	protected abstract void setClient(ActionContext crawlContext);

	@Override
	public void invoke(Action action, ActionContext context) throws Exception {
		this.setClient(context);
		action.execute(context);
	}

	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

	public void setContextWriter(ContextWriter contextWriter) {
		this.contextWriter = contextWriter;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
}
