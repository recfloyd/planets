package org.rec.planets.jupiter.action.network.client.rest;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.action.network.client.Client;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestClient implements Client {
	private static final String REQUEST_COOKIE_HEAD = "Cookie";
	private static final String RESPONSE_COOKIE_HEAD = "Set-Cookie";
	private static final String RESPONSE_COOKIE_HEAD2 = "Set-Cookie2";
	private RestTemplate restTemplate;

	private String buildCookieRequest(List<HttpCookie> cookies) {
		StringBuilder buffer = new StringBuilder();
		HttpCookie cookie = null;
		for (int i = 0; i < cookies.size(); i++) {
			cookie = cookies.get(i);
			if (i > 0) {
				buffer.append("; ");
			}
			buffer.append(cookie.getName());
			buffer.append('=');
			String s = cookie.getValue();
			if (s != null) {
				buffer.append(s);
			}
		}
		return buffer.toString();
	}

	private List<HttpCookie> readCookieResponse(HttpHeaders httpHeaders) {
		List<HttpCookie> result = new LinkedList<HttpCookie>();
		String headerName = null;
		for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
			headerName = entry.getKey();
			if (headerName.equalsIgnoreCase(RESPONSE_COOKIE_HEAD)
					|| headerName.equalsIgnoreCase(RESPONSE_COOKIE_HEAD2)) {
				for (String value : entry.getValue()) {
					result.addAll(HttpCookie.parse(value));
				}
			}
		}
		return result;
	}

	private <T> Response<T> getResponse(Request request, Class<T> responseType)
			throws Exception {
		HttpEntity<String> requestEntity = null;

		HttpHeaders headers = new HttpHeaders();

		HttpHeaders httpHeaders = request.getHeaders();
		if (httpHeaders != null)
			headers.putAll(httpHeaders);

		List<HttpCookie> cookies = request.getCookies();
		if (CollectionUtils.isNotEmpty(cookies))
			headers.add(REQUEST_COOKIE_HEAD, buildCookieRequest(cookies));

		requestEntity = new HttpEntity<String>(headers);

		Map<String, String> uriVariables = new HashMap<String, String>();
		MultiValueMap<String, String> params = request.getParams();
		if (params != null)
			uriVariables.putAll(params.toSingleValueMap());

		HttpMethod method = request.getMethod();
		method = method == null ? HttpMethod.GET : method;

		ResponseEntity<T> responseEntity = restTemplate.exchange(
				request.getUrl(), method, requestEntity, responseType,
				uriVariables);

		List<HttpCookie> responseCookies = null;
		HttpHeaders responseHeaders = responseEntity.getHeaders();
		if (responseHeaders != null)
			responseCookies = this.readCookieResponse(responseHeaders);

		Response<T> response = new Response<T>();
		response.setCookies(responseCookies);
		response.setHttpResponse(responseEntity);

		return response;
	}

	@Override
	public Response<String> requestText(Request request) throws Exception {
		return this.getResponse(request, String.class);
	}

	@Override
	public Response<byte[]> requestByteArray(Request request) throws Exception {
		return this.getResponse(request, byte[].class);
	}

	@Override
	public void destroy() {
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public static void main(String[] args) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		Request request = new Request();
		request.setUrl("http://www.tmall.com");
		request.setMethod(HttpMethod.GET);
		request.setEncoding("UTF-8");

		RestClient client = new RestClient();
		client.setRestTemplate(restTemplate);
		Response<String> text = client.requestText(request);
		System.out.println(text);
	}
}
