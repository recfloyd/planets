package org.rec.planets.jupiter.action.network;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.request.RequestBuilder;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;
import org.rec.planets.jupiter.context.accessor.ContextWriter;

/**
 * 抽象下载器
 * 
 * @author rec
 * 
 */
public abstract class AbstractDownloader implements Action {
	/**
	 * 请求创建器
	 */
	protected RequestBuilder requestBuilder;
	protected ContextReader contextReader;
	protected ContextWriter contextWriter;
	protected String clientKey;
	protected String responseKey;

	@Override
	public void execute(ActionContext context) throws Exception {
		Client client = (Client) contextReader
				.read(context, clientKey);

		Request request = requestBuilder.build(context);

		Response<?> response = request(client, request);

		contextWriter.write(context, responseKey, response);
	}

	protected abstract <T> Response<?> request(Client client, Request request)
			throws Exception;

	public void setRequestBuilder(RequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
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

	public void setResponseKey(String responseKey) {
		this.responseKey = responseKey;
	}
}
