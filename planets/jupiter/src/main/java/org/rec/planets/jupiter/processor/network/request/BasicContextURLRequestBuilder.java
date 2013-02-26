package org.rec.planets.jupiter.processor.network.request;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.accessor.Accessable;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessor;
import org.rec.planets.jupiter.processor.network.bean.Request;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Strings;

public class BasicContextURLRequestBuilder implements RequestBuilder,
		Accessable {
	private String sourceKey;
	private CrawlContextAccessor crawlContextAccessor;

	@Override
	public Request build(CrawlContext crawlContext) throws Exception {
		String url = (String) crawlContextAccessor.get(crawlContext, sourceKey);
		if (Strings.isNullOrEmpty(url))
			throw new IllegalArgumentException(
					"cannot get url from context by key " + sourceKey
							+ " of crawlURL " + crawlContext.getCrawlURL());
		Request request = new Request();
		request.setUrl(url);
		request.setMethod(RequestMethod.GET);

		return request;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	@Override
	public void setCrawlContextAccessor(
			CrawlContextAccessor crawlContextAccessor) {
		this.crawlContextAccessor = crawlContextAccessor;
	}
}
