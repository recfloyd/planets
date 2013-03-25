package org.rec.planets.jupiter.action.network.bean;

import java.net.HttpCookie;
import java.util.List;

import org.rec.planets.mercury.domain.AbstractBean;
import org.springframework.http.ResponseEntity;

/**
 * http响应实体
 * 
 * @author rec
 * 
 * @param <T>
 */
public class Response<T> extends AbstractBean {
	private ResponseEntity<T> httpResponse;
	private List<HttpCookie> cookies;

	public ResponseEntity<T> getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(ResponseEntity<T> httpResponse) {
		this.httpResponse = httpResponse;
	}

	public List<HttpCookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<HttpCookie> cookies) {
		this.cookies = cookies;
	}
}
