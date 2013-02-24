package org.rec.planets.jupiter.processor.network.request;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.network.bean.Request;
import org.springframework.web.bind.annotation.RequestMethod;

public final class BasicRequestBuilder implements RequestBuilder {
	private BasicRequestBuilder() {
	}

	private static volatile BasicRequestBuilder instance;

	public static BasicRequestBuilder getInstance() {
		if (instance == null) {
			synchronized (BasicRequestBuilder.class) {
				if (instance == null) {
					instance = new BasicRequestBuilder();
				}
			}
		}
		return instance;
	}

	@Override
	public Request build(CrawlContext crawlContext) throws Exception {
		Request request=new Request();
		request.setUrl(crawlContext.getCrawlURL().getUrl());
		request.setMethod(RequestMethod.GET);
		
		return request;
	}

}
