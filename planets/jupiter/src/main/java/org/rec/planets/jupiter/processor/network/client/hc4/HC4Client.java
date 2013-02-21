package org.rec.planets.jupiter.processor.network.client.hc4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.rec.planets.jupiter.processor.network.bean.Request;
import org.rec.planets.jupiter.processor.network.bean.Response;
import org.rec.planets.jupiter.processor.network.client.Client;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;

public abstract class HC4Client implements Client {
	protected DefaultHttpClient httpClient;

	public HC4Client(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Override
	public <T> Response<T> request(Request request) throws Exception {
		RequestMethod method = request.getMethod();
		method = method == null ? RequestMethod.GET : method;
		String encoding = request.getEncoding();
		encoding = Strings.isNullOrEmpty(encoding) ? Charsets.UTF_8.name()
				: encoding;

		HttpUriRequest httpRequest = null;
		switch (method) {
		case POST:
			httpRequest = new HttpPost(request.getUrl());
			if (request.getParams() != null) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> kv : request.getParams()
						.entries()) {
					params.add(new BasicNameValuePair(kv.getKey(), kv
							.getValue()));
				}
				((HttpPost) httpRequest).setEntity(new UrlEncodedFormEntity(
						params, encoding));
			}
			break;
		default:
			URIBuilder builder = new URIBuilder(request.getUrl());
			if (request.getParams() != null) {
				for (Map.Entry<String, String> kv : request.getParams()
						.entries()) {
					builder.addParameter(kv.getKey(), kv.getValue());
				}
			}
			httpRequest = new HttpGet(builder.build());
			break;
		}

		if (request.getHeaders() != null) {
			for (Map.Entry<String, String> kv : request.getHeaders().entrySet()) {
				httpRequest.addHeader(kv.getKey(), kv.getValue());
			}
		}

		HttpContext context = new BasicHttpContext();
		if (request.getCookies() != null) {
			CookieStore cookieStore = httpClient.getCookieStore();
			if (cookieStore == null)
				cookieStore = new BasicCookieStore();
			context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			for (Map.Entry<String, String> kv : request.getCookies().entrySet()) {
			}
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
