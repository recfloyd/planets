package org.rec.planets.jupiter.processor.network.client.hc4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
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
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.rec.planets.jupiter.processor.network.bean.Request;
import org.rec.planets.jupiter.processor.network.bean.Response;
import org.rec.planets.jupiter.processor.network.client.Client;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;

public class HC4Client implements Client {
	protected DefaultHttpClient httpClient;

	public HC4Client(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	private <T> HttpEntity request(Request request, Response<T> emptyResponse)
			throws Exception {
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
		CookieStore cookieStore = new BasicCookieStore();
		context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		if (request.getCookies() != null) {
			for (Map.Entry<String, String> kv : request.getCookies().entrySet()) {
				cookieStore.addCookie(new BasicClientCookie(kv.getKey(), kv
						.getValue()));
			}
		}

		HttpResponse response = null;

		try {
			response = httpClient.execute(httpRequest, context);
		} finally {
			httpRequest.abort();
		}

		StatusLine statusLine = response.getStatusLine();
		if (statusLine != null) {
			response.setStatusCode(statusLine.getStatusCode());
		}

		Header[] allHeaders = response.getAllHeaders();
		if (allHeaders != null && allHeaders.length > 0) {
			Map<String, String> responseHeader = new HashMap<String, String>();
			for (Header header : allHeaders) {
				responseHeader.put(header.getName(), header.getValue());
			}
			emptyResponse.setHeaders(responseHeader);
		}

		List<Cookie> allCookies = cookieStore.getCookies();
		if (!CollectionUtils.isEmpty(allCookies)) {
			Map<String, String> responseCookies = new HashMap<String, String>();
			for (Cookie cookie : allCookies) {
				responseCookies.put(cookie.getName(), cookie.getValue());
			}
			emptyResponse.setCookies(responseCookies);
		}
		
		HttpEntity entity = response.getEntity();
		
		Header contentEncodingHeader = entity.getContentEncoding();
		if (contentEncodingHeader != null) {
			emptyResponse.setContentEncoding(contentEncodingHeader.getValue());
		}

		Header contentType = entity.getContentType();
		if (contentType != null)
			emptyResponse.setContentType(contentType.getValue());

		emptyResponse.setContentLength(entity.getContentLength());

		return entity;
	}

	@Override
	public Response<String> requestString(Request request) throws Exception {
		Response<String> emptyResponse = new Response<String>();
		HttpEntity entity = this.request(request, emptyResponse);
		String encoding = emptyResponse.getContentEncoding();
		if (Strings.isNullOrEmpty(encoding))
			encoding = request.getEncoding();
		if (Strings.isNullOrEmpty(encoding))
			encoding = Charsets.UTF_8.name();
		try {
			emptyResponse.setContent(EntityUtils.toString(entity, encoding));
		} finally {
			EntityUtils.consumeQuietly(entity);
		}

		return emptyResponse;
	}

	@Override
	public Response<byte[]> requestByteArray(Request request) throws Exception {
		Response<byte[]> emptyResponse = new Response<byte[]>();
		HttpEntity entity = this.request(request, emptyResponse);
		try {
			emptyResponse.setContent(EntityUtils.toByteArray(entity));
		} finally {
			EntityUtils.consumeQuietly(entity);
		}

		return emptyResponse;
	}

	@Override
	public void close() {
		httpClient.getConnectionManager().shutdown();
	}

}
