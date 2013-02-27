package org.rec.planets.jupiter.processor.network;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.CrawlContextReader;
import org.rec.planets.jupiter.processor.accessor.CrawlContextWriter;
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
public abstract class AbstractDownloader implements CrawlProcessor {
	/**
	 * 请求创建器
	 */
	protected RequestBuilder requestBuilder;

	protected CrawlContextReader crawlContextReader;
	protected CrawlContextWriter crawlContextWriter;
	protected String clientKey;
	protected String responseKey;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		Client client = (Client) crawlContextReader
				.get(crawlContext, clientKey);

		Request request = requestBuilder.build(crawlContext);

		Response<?> response = request(client, request);

		crawlContextWriter.set(crawlContext, responseKey, response);
	}

	protected abstract <T> Response<?> request(Client client, Request request)
			throws Exception;

	public void setRequestBuilder(RequestBuilder requestBuilder) {
		this.requestBuilder = requestBuilder;
	}

	public void setCrawlContextReader(CrawlContextReader crawlContextReader) {
		this.crawlContextReader = crawlContextReader;
	}

	public void setCrawlContextWriter(CrawlContextWriter crawlContextWriter) {
		this.crawlContextWriter = crawlContextWriter;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public void setResponseKey(String responseKey) {
		this.responseKey = responseKey;
	}
}
