package org.rec.planets.jupiter.processor.network;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.bean.CrawlContextConstants;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.Accessable;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessor;
import org.rec.planets.jupiter.processor.network.bean.Request;
import org.rec.planets.jupiter.processor.network.bean.Response;
import org.rec.planets.jupiter.processor.network.client.Client;
import org.rec.planets.jupiter.processor.network.request.RequestBuilder;

/**
 * 抽象下载器
 * 
 * @author rec
 * 
 */
public abstract class AbstractDownloader implements CrawlProcessor, Accessable {
	/**
	 * 请求创建器
	 */
	protected RequestBuilder requestBuilder;
	/**
	 * 结果键
	 */
	protected String resultKey;

	protected CrawlContextAccessor crawlContextAccessor;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		Client client = (Client) crawlContext.getContext().get(
				CrawlContextConstants.KEY_CLIENT);
		if (client == null)
			throw new RuntimeException("can not get client via key "
					+ CrawlContextConstants.KEY_CLIENT);

		Request request = requestBuilder.build(crawlContext);

		Response<?> response = request(client, request);

		crawlContextAccessor.set(crawlContext, resultKey, response);
	}

	protected abstract <T> Response<?> request(Client client, Request request)
			throws Exception;

	public void setRequestBuilder(RequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	@Override
	public void setCrawlContextAccessor(
			CrawlContextAccessor crawlContextAccessor) {
		this.crawlContextAccessor = crawlContextAccessor;
	}
}
