package org.rec.planets.jupiter.action.network.bean;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.rec.planets.mercury.domain.AbstractBean;

import com.google.common.collect.Multimap;

/**
 * http请求实体
 * 
 * @author rec
 * 
 */
public class Request extends AbstractBean {
	private RequestMethod method;
	private String url;
	private Multimap<String, String> headers;
	private Multimap<String, String> params;
	private String encoding;
	private Map<String, String> cookies;
	private Map<String, Object> clientSetting;

	public RequestMethod getMethod() {
		return method;
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Multimap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Multimap<String, String> headers) {
		this.headers = headers;
	}

	public Multimap<String, String> getParams() {
		return params;
	}

	public void setParams(Multimap<String, String> params) {
		this.params = params;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public Map<String, Object> getClientSetting() {
		return clientSetting;
	}

	public void setClientSetting(Map<String, Object> clientSetting) {
		this.clientSetting = clientSetting;
	}

	public String getSingleHeader(String name) {
		if (this.headers == null)
			return null;
		Collection<String> values = this.headers.get(name);
		if (CollectionUtils.isEmpty(values))
			return null;
		return values.iterator().next();
	}

	public String getSingleParam(String name) {
		if (this.params == null)
			return null;
		Collection<String> values = this.params.get(name);
		if (CollectionUtils.isEmpty(values))
			return null;
		return values.iterator().next();
	}
}
