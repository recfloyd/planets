package org.rec.planets.jupiter.processor.network;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.bean.CrawlContextConstants;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.network.bean.Request;
import org.rec.planets.jupiter.processor.network.bean.Response;
import org.rec.planets.jupiter.processor.network.client.Client;
import org.rec.planets.jupiter.processor.network.request.RequestBuilder;

public abstract class AbstractDownloadProcessor implements CrawlProcessor {
	protected RequestBuilder requestBuilder;
	protected String resultKey;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		Client client = (Client) crawlContext.getContext().get(
				CrawlContextConstants.KEY_CLIENT);
		if (client == null)
			throw new RuntimeException("can not get client via key "
					+ CrawlContextConstants.KEY_CLIENT);

		Request request = requestBuilder.build(crawlContext);

		Response<?> response = request(client, request);

		crawlContext.getContext().put(resultKey, response);
	}

	protected abstract <T> Response<?> request(Client client, Request request)
			throws Exception;

	public void setRequestBuilder(RequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}
}
