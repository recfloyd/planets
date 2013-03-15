package org.rec.planets.jupiter.action.network;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.request.RequestBuilder;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.AbstractReadWriteSupport;
import org.springframework.util.Assert;

/**
 * 抽象下载器
 * 
 * @author rec
 * 
 */
public abstract class AbstractDownloader extends AbstractReadWriteSupport
		implements Action {
	/**
	 * 请求创建器
	 */
	protected RequestBuilder requestBuilder;

	@Override
	public void execute(ActionContext context) throws Exception {
		Client client = (Client) getSource(context);

		Assert.notNull(client);

		Request request = requestBuilder.build(context);

		Response<?> response = request(client, request);

		if (response != null)
			writeResult(context, response);
	}

	protected abstract <T> Response<?> request(Client client, Request request)
			throws Exception;

	public void setRequestBuilder(RequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}
}
