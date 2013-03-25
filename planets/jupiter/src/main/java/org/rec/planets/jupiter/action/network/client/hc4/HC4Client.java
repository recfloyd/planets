package org.rec.planets.jupiter.action.network.client.hc4;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.action.network.client.Client;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class HC4Client implements Client {
	private final HttpClient httpClient;

	public HC4Client(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	private static final ResponseHandler<String> TEXT_RESPONSE_HANDLER = new ResponseHandler<String>() {
		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			return entity == null ? null : EntityUtils.toString(entity);
		}
	};
	private static final ResponseHandler<byte[]> BYTE_ARRAY_RESPONSE_HANDLER = new ResponseHandler<byte[]>() {
		@Override
		public byte[] handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			HttpEntity entity = response.getEntity();
			return entity == null ? null : EntityUtils.toByteArray(entity);
		}
	};

	private <T> Response<T> request(Request request,
			ResponseHandler<T> responseHandler) throws Exception {
		HttpMethod method = request.getMethod();
		method = method == null ? HttpMethod.GET : method;
		String encoding = request.getEncoding();
		encoding = Strings.isNullOrEmpty(encoding) ? Charsets.UTF_8.name()
				: encoding;

		HttpUriRequest httpRequest = null;
		switch (method) {
		case POST:
			httpRequest = new HttpPost(request.getUrl());
			if (request.getParams() != null) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				for (Map.Entry<String, List<String>> entry : request
						.getParams().entrySet()) {
					for (String value : entry.getValue()) {
						params.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				((HttpPost) httpRequest).setEntity(new UrlEncodedFormEntity(
						params, encoding));
			}
			break;
		default:
			URIBuilder builder = new URIBuilder(request.getUrl());
			if (request.getParams() != null) {
				for (Map.Entry<String, List<String>> entry : request
						.getParams().entrySet()) {
					for (String value : entry.getValue()) {
						builder.addParameter(entry.getKey(), value);
					}
				}
			}
			httpRequest = new HttpGet(builder.build());
			break;
		}

		MultiValueMap<String, String> headers = request.getHeaders();
		if (headers != null) {
			for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
				for (String value : entry.getValue()) {
					httpRequest.addHeader(entry.getKey(), value);
				}
			}
		}

		HttpContext context = new BasicHttpContext();
		CookieStore cookieStore = new BasicCookieStore();
		context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		List<HttpCookie> cookies = request.getCookies();
		if (cookies != null) {
			for (HttpCookie cookie : cookies) {
				cookieStore.addCookie(new BasicClientCookie(cookie.getName(),
						cookie.getValue()));
			}
		}

		HttpResponse response = null;
		try {
			response = httpClient.execute(httpRequest, context);
		} catch (Exception e) {
			httpRequest.abort();
			throw e;
		}

		HttpStatus statusCode = HttpStatus.valueOf(response.getStatusLine()
				.getStatusCode());

		Header[] responseHeaders = response.getAllHeaders();
		HttpHeaders httpHeaders = new HttpHeaders();
		for (Header header : responseHeaders) {
			httpHeaders.add(header.getName(), header.getValue());
		}

		List<HttpCookie> httpCookies = null;
		List<Cookie> responseCookies = cookieStore.getCookies();
		if (!CollectionUtils.isEmpty(responseCookies)) {
			httpCookies = new ArrayList<HttpCookie>(responseCookies.size());
			HttpCookie hc = null;
			for (Cookie cookie : responseCookies) {
				hc = new HttpCookie(cookie.getName(), cookie.getValue());
				hc.setComment(cookie.getComment());
				hc.setCommentURL(cookie.getCommentURL());
				hc.setDiscard(!cookie.isPersistent());
				hc.setDomain(cookie.getDomain());
				hc.setMaxAge(cookie.getExpiryDate() == null ? -1
						: ((cookie.getExpiryDate().getTime() - System
								.currentTimeMillis()) / 1000));
				hc.setPath(cookie.getPath());
				hc.setPortlist(cookie.getPorts() == null ? null : Joiner
						.on(',').join(Arrays.asList(cookie.getPorts())));
				hc.setSecure(cookie.isSecure());
				hc.setVersion(cookie.getVersion());
				httpCookies.add(hc);
			}
		}

		Response<T> result = new Response<T>();
		result.setCookies(httpCookies);
		result.setHttpResponse(new ResponseEntity<T>(responseHandler
				.handleResponse(response), httpHeaders, statusCode));

		return result;
	}

	@Override
	public Response<String> requestText(Request request) throws Exception {
		return this.request(request, TEXT_RESPONSE_HANDLER);
	}

	@Override
	public Response<byte[]> requestByteArray(Request request) throws Exception {
		return this.request(request, BYTE_ARRAY_RESPONSE_HANDLER);
	}

	@Override
	public void destroy() {
		httpClient.getConnectionManager().shutdown();
	}

}
