package org.rec.planets.jupiter.action.network.request;

import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.AbstractReadSupport;
import org.springframework.web.bind.annotation.RequestMethod;

public class BasicContextURLRequestBuilder extends AbstractReadSupport
		implements RequestBuilder {

	@Override
	public Request build(ActionContext context) throws Exception {
		String url = (String) getSource(context);
		Request request = new Request();
		request.setUrl(url);
		request.setMethod(RequestMethod.GET);

		return request;
	}
}
