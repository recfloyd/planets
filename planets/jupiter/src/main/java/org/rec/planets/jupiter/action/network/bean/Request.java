package org.rec.planets.jupiter.action.network.bean;

import java.net.HttpCookie;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.rec.planets.mercury.domain.AbstractBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

/**
 * http请求实体
 * 
 * @author rec
 * 
 */
public class Request extends AbstractBean {
	private String url;
	private HttpMethod method;
	private HttpHeaders headers;
	private MultiValueMap<String, String> params;
	private String encoding;
	private List<HttpCookie> cookies;
	private Map<String, Object> clientSetting;

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	public MultiValueMap<String, String> getParams() {
		return params;
	}

	public void setParams(MultiValueMap<String, String> params) {
		this.params = params;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public List<HttpCookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<HttpCookie> cookies) {
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
