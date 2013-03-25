package org.rec.planets.jupiter.action.network.request;

import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.http.HttpMethod;

import com.google.common.base.Strings;

public class BasicContextURLRequestPrepairAction extends
		AbstractReadProcessWriteAction {

	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		String url = (String) getSource(context);
		if (Strings.isNullOrEmpty(url))
			return null;
		else {
			Request request = new Request();
			request.setUrl(url);
			request.setMethod(HttpMethod.GET);

			return request;
		}
	}

}
