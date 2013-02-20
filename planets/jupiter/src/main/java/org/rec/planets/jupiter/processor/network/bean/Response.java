package org.rec.planets.jupiter.processor.network.bean;

import java.util.Map;

import org.rec.planets.mercury.domain.AbstractBean;

import com.google.common.collect.Multimap;

public class Response<T> extends AbstractBean {
	private int statusCode;
	private String contentType;
	private String contentEncoding;
	private long contentLength;
	private Multimap<String, String> headers;
	private Map<String, String> cookies;
	private T content;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentEncoding() {
		return contentEncoding;
	}
	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}
	public long getContentLength() {
		return contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	public Multimap<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Multimap<String, String> headers) {
		this.headers = headers;
	}
	public Map<String, String> getCookies() {
		return cookies;
	}
	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
}
