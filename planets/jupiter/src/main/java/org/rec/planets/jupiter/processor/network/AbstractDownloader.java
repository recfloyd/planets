package org.rec.planets.jupiter.processor.network;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.bean.CrawlContextConstants;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessable;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessor;
import org.rec.planets.jupiter.processor.network.bean.Request;
import org.rec.planets.jupiter.processor.network.bean.Response;
import org.rec.planets.jupiter.processor.network.client.Client;
import org.rec.planets.jupiter.processor.network.request.RequestBuilder;
import org.springframework.util.Assert;

/**
 * 抽象下载器
 * 
 * @author rec
 * 
 */
public abstract class AbstractDownloader implements CrawlProcessor,
		CrawlContextAccessable {
	/**
	 * 请求创建器
	 */
	protected RequestBuilder requestBuilder;

	protected CrawlContextAccessor crawlContextAccessor;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		Client client = (Client) crawlContext.getContext().get(
				CrawlContextConstants.KEY_CLIENT);
		Assert.notNull(client, "can not get client via key "
				+ CrawlContextConstants.KEY_CLIENT);

		Request request = requestBuilder.build(crawlContext);

		Response<?> response = request(client, request);

		crawlContextAccessor.set(crawlContext, response);
	}

	protected abstract <T> Response<?> request(Client client, Request request)
			throws Exception;

	public void setRequestBuilder(RequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}

	@Override
	public void setCrawlContextAccessor(
			CrawlContextAccessor crawlContextAccessor) {
		this.crawlContextAccessor = crawlContextAccessor;
	}
}
