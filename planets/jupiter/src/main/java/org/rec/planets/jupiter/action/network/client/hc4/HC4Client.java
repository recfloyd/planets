package org.rec.planets.jupiter.action.network.client.hc4;

import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.RequestMethod;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.action.network.client.Client;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class HC4Client implements Client {
	private final HttpClient httpClient;

	public HC4Client(HttpClient httpClient) {
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

		Multimap<String, String> headers = request.getHeaders();
		if (headers != null) {
			for (Map.Entry<String, String> kv : headers.entries()) {
				httpRequest.addHeader(kv.getKey(), kv.getValue());
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

		StatusLine statusLine = response.getStatusLine();
		if (statusLine != null) {
			emptyResponse.setStatusCode(statusLine.getStatusCode());
		}

		Header[] allHeaders = response.getAllHeaders();
		if (allHeaders != null && allHeaders.length > 0) {
			Multimap<String, String> responseHeader = ArrayListMultimap
					.create();
			for (Header header : allHeaders) {
				responseHeader.put(header.getName(), header.getValue());
			}
			emptyResponse.setHeaders(responseHeader);
		}

		List<Cookie> allCookies = cookieStore.getCookies();
		if (!CollectionUtils.isEmpty(allCookies)) {
			List<HttpCookie> responseCookies = new ArrayList<HttpCookie>();
			HttpCookie hc = null;
			for (Cookie cookie : allCookies) {
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
				responseCookies.add(hc);
			}
			emptyResponse.setCookies(responseCookies);
		}

		HttpEntity entity = response.getEntity();

		if (entity != null) {
			ContentType contentType = ContentType.get(entity);

			if (contentType != null) {
				emptyResponse.setMimeType(contentType.getMimeType());
				Charset charset = contentType.getCharset();
				if (charset != null)
					emptyResponse.setContentEncoding(charset.name());
			}
			if (emptyResponse.getContentEncoding() == null) {
				Header contentEncodingHeader = entity.getContentEncoding();
				if (contentEncodingHeader != null)
					emptyResponse.setContentEncoding(contentEncodingHeader
							.getValue());
			}
			emptyResponse.setContentLength(entity.getContentLength());
		}

		return entity;
	}

	@Override
	public Response<String> requestText(Request request) throws Exception {
		Response<String> emptyResponse = new Response<String>();
		HttpEntity entity = this.request(request, emptyResponse);
		if (entity != null) {
			String content = null;
			try {
				content = EntityUtils.toString(entity);
			} finally {
				EntityUtils.consumeQuietly(entity);
			}
			emptyResponse.setContent(content);
			// 如果contentLength没有的话,那么使用body的长度作为近似长度
			if (emptyResponse.getContentLength() < 0 && content != null) {
				emptyResponse.setContentLength(content.getBytes().length);
			}
		}

		return emptyResponse;
	}

	@Override
	public Response<byte[]> requestByteArray(Request request) throws Exception {
		Response<byte[]> emptyResponse = new Response<byte[]>();
		HttpEntity entity = this.request(request, emptyResponse);
		if (entity != null) {
			byte[] content = null;
			try {
				content = EntityUtils.toByteArray(entity);
			} finally {
				EntityUtils.consumeQuietly(entity);
			}
			emptyResponse.setContent(content);
			if (emptyResponse.getContentLength() < 0 && content != null) {
				emptyResponse.setContentLength(content.length);
			}
		}

		return emptyResponse;
	}

	@Override
	public void destroy() {
		httpClient.getConnectionManager().shutdown();
	}

}
