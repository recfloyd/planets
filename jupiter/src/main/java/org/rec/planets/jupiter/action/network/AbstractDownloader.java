package org.rec.planets.jupiter.action.network;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;
import org.rec.planets.jupiter.context.accessor.ContextWriter;
import org.rec.planets.mercury.domain.AbstractBean;

/**
 * 抽象下载器
 * 
 * @author rec
 * 
 */
public abstract class AbstractDownloader extends AbstractBean implements Action {
	private static final long serialVersionUID = -3056956080600420620L;
	protected ContextReader clientReader;
	protected String clientKey;
	protected ContextReader requestReader;
	protected String requestKey;
	protected ContextWriter responseWriter;
	protected String responseKey;

	protected Object getSource(ActionContext context, ContextReader reader,
			String key) throws Exception {
		Object source = reader.read(context, key);

		if (source == null) {
			throw new RuntimeException(
					"cannot get source from actionContext by key " + key
							+ " of crawlURL " + context.getCrawlURL());
		}
		return source;
	}

	@Override
	public void execute(ActionContext context) throws Exception {
		Client client = (Client) getSource(context, clientReader, clientKey);
		Request request = (Request) getSource(context, requestReader,
				requestKey);

		Response<?> response = request(client, request);

		responseWriter.write(context, responseKey, response);
	}

	protected abstract <T> Response<?> request(Client client, Request request)
			throws Exception;

	public void setClientReader(ContextReader clientReader) {
		this.clientReader = clientReader;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public void setRequestReader(ContextReader requestReader) {
		this.requestReader = requestReader;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public void setResponseWriter(ContextWriter responseWriter) {
		this.responseWriter = responseWriter;
	}

	public void setResponseKey(String responseKey) {
		this.responseKey = responseKey;
	}
}
