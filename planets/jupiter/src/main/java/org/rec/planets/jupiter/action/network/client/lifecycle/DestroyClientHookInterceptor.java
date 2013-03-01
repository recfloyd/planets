package org.rec.planets.jupiter.action.network.client.lifecycle;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.interceptor.Interceptor;
import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;

/**
 * http客户端销毁拦截器,执行完下载之后,此拦截器将客户端销毁
 * 
 * @author rec
 * 
 */
public class DestroyClientHookInterceptor implements Interceptor {
	private ContextReader contextReader;
	private String clientKey;

	@Override
	public void invoke(Action action, ActionContext context) throws Exception {
		try {
			action.execute(context);
		} finally {
			Client client = (Client) contextReader.read(context, clientKey);
			if (client != null)
				client.destroy();
		}
	}

	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

}