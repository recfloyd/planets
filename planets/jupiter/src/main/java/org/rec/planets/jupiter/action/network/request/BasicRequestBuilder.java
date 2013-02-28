package org.rec.planets.jupiter.action.network.request;

import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 最基本的请求实体创建器
 * @author rec
 *
 */
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
	public Request build(ActionContext crawlContext) throws Exception {
		Request request=new Request();
		request.setUrl(crawlContext.getCrawlURL().getUrl());
		request.setMethod(RequestMethod.GET);
		
		return request;
	}

}
