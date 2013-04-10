package org.rec.planets.jupiter.action.network.cookie;

import java.net.HttpCookie;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.rec.planets.jupiter.action.network.cookie.bean.ClientCookies;
import org.springframework.beans.factory.DisposableBean;

public final class ClientCookieCache implements DisposableBean {
	private ConcurrentMap<String, ClientCookies> cache = new ConcurrentHashMap<String, ClientCookies>();

	public void addCookies(String key, List<HttpCookie> cookies) {
		ClientCookies cc = cache.putIfAbsent(key, new ClientCookies(cookies));
		if (cc != null) {
			cc.addAll(cookies);
		}
	}

	public void addCookie(String key, HttpCookie httpCookie) {
		ClientCookies cc = cache.putIfAbsent(key, new ClientCookies());
		if (cc != null) {
			cc.add(httpCookie);
		}
	}

	@SuppressWarnings("unchecked")
	public List<HttpCookie> getCookies(String key) {
		ClientCookies cc = cache.get(key);
		if (cc == null) {
			return Collections.EMPTY_LIST;
		} else {
			return cc.get();
		}
	}

	public void clearCookie(String key) {
		ClientCookies cc = cache.get(key);
		if (cc != null) {
			cc.clear();
		}
	}

	public void removeCookie(String key, String name) {
		ClientCookies cc = cache.get(key);
		if (cc != null) {
			cc.removeByName(name);
		}
	}

	public void removeCookies(String key, Set<String> names) {
		ClientCookies cc = cache.get(key);
		if (cc != null) {
			cc.removeAllByNames(names);
		}
	}

	public void retainCookies(String key, Set<String> names) {
		ClientCookies cc = cache.get(key);
		if (cc != null) {
			cc.retain(names);
		}
	}

	public int getReadCount(String key) {
		ClientCookies cc = cache.get(key);
		if (cc == null) {
			return 0;
		} else {
			return cc.getReadCount();
		}
	}

	@Override
	public void destroy() throws Exception {
		cache.clear();
	}

}
