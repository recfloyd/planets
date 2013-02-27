package org.rec.planets.jupiter.processor.network.request;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.accessor.CrawlContextReader;
import org.rec.planets.jupiter.processor.network.bean.Request;
import org.springframework.web.bind.annotation.RequestMethod;

public class BasicContextURLRequestBuilder implements RequestBuilder {
	protected CrawlContextReader crawlContextReader;
	protected String urlKey;

	@Override
	public Request build(CrawlContext crawlContext) throws Exception {
		String url = (String) crawlContextReader.get(crawlContext, urlKey);
		Request request = new Request();
		request.setUrl(url);
		request.setMethod(RequestMethod.GET);

		return request;
	}

	public void setCrawlContextReader(CrawlContextReader crawlContextReader) {
		this.crawlContextReader = crawlContextReader;
	}

	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}

}
