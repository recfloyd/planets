package org.rec.planets.jupiter.action.parser.dom;

import org.jsoup.Jsoup;
import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.context.ActionContext;

import com.google.common.base.Strings;

public class DomPrepairAction extends AbstractReadProcessWriteAction {

	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		@SuppressWarnings("unchecked")
		Response<String> response = (Response<String>) source;
		if (response != null) {
			String content = response.getHttpResponse().getBody();
			if (Strings.isNullOrEmpty(content))
				return null;
			else {
				return Jsoup.parse(content);
			}
		}
		return null;
	}

}
