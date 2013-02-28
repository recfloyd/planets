package org.rec.planets.jupiter.action.network.request;

import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;
import org.springframework.web.bind.annotation.RequestMethod;

public class BasicContextURLRequestBuilder implements RequestBuilder {
	protected ContextReader contextReader;
	protected String urlKey;

	@Override
	public Request build(ActionContext context) throws Exception {
		String url = (String) contextReader.read(context, urlKey);
		Request request = new Request();
		request.setUrl(url);
		request.setMethod(RequestMethod.GET);

		return request;
	}

	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}
}
