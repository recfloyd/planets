package org.rec.planets.jupiter.processor.network.request;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessable;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessor;
import org.rec.planets.jupiter.processor.network.bean.Request;
import org.springframework.web.bind.annotation.RequestMethod;

public class BasicContextURLRequestBuilder implements RequestBuilder,
		CrawlContextAccessable {
	private CrawlContextAccessor crawlContextAccessor;

	@Override
	public Request build(CrawlContext crawlContext) throws Exception {
		String url = (String) crawlContextAccessor.get(crawlContext);
		Request request = new Request();
		request.setUrl(url);
		request.setMethod(RequestMethod.GET);

		return request;
	}

	@Override
	public void setCrawlContextAccessor(
			CrawlContextAccessor crawlContextAccessor) {
		this.crawlContextAccessor = crawlContextAccessor;
	}
}
