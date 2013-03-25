package org.rec.planets.jupiter.action.network.cookie;

import java.net.HttpCookie;
import java.util.List;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.AbstractReadSupport;
import org.springframework.util.CollectionUtils;

public class CookieSaveAction extends AbstractReadSupport implements Action {
	private ClientCookieCache clientCookieCache;
	private String cookieKey;

	@Override
	public void execute(ActionContext context) throws Exception {
		Response<?> response = (Response<?>) getSource(context);
		if (response != null) {
			List<HttpCookie> cookies = response.getCookies();
			if (!CollectionUtils.isEmpty(cookies))
				clientCookieCache.addCookies(cookieKey, cookies);
		}
	}

	public void setClientCookieCache(ClientCookieCache clientCookieCache) {
		this.clientCookieCache = clientCookieCache;
	}

	public void setCookieKey(String cookieKey) {
		this.cookieKey = cookieKey;
	}
}
