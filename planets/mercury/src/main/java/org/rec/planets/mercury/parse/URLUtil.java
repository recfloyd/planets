package org.rec.planets.mercury.parse;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;

public abstract class URLUtil {
	/**
	 * 获取url的绝对路径
	 * 
	 * @param url
	 * @param base
	 * @return
	 */
	public static String getAbsolutePath(String url, String base) {
		if (Strings.isNullOrEmpty(base))
			return url;
		URI baseURI = URI.create(base);
		return baseURI.resolve(url).normalize().toString();
	}

	/**
	 * 获取url的相对路径,指域名之后的部分
	 * 
	 * @param url
	 * @return
	 */
	public static String getRelativePath(String url) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		builder.scheme(null);
		builder.host(null);
		builder.port(-1);
		builder.userInfo(null);
		return builder.build().toUriString();
	}

	/**
	 * 对整个url进行编码
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 */
	public static String encodeURL(String url, String encoding) {
		try {
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromUriString(url);
			return builder.build().encode(encoding).toUriString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对整个url进行编码
	 * 
	 * @param url
	 * @return
	 */
	public static String encodeURL(String url) {
		return encodeURL(url, Charsets.UTF_8.name());
	}

	/**
	 * 对url进行分步编码
	 * 
	 * @param url
	 * @param encodePath
	 * @param encodeQuery
	 * @param encodeRef
	 * @param encoding
	 * @return
	 */
	public static String encodeURL(String url, boolean encodePath,
			boolean encodeQuery, boolean encodeRef, String encoding) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		UriComponents components = builder.build();

		try {
			if (encodePath)
				builder.replacePath(UriUtils.encodePath(components.getPath(),
						encoding));

			if (encodeQuery)
				builder.replaceQuery(UriUtils.encodeQuery(
						components.getQuery(), encoding));

			if (encodeRef)
				builder.fragment(UriUtils.encodeFragment(
						components.getFragment(), encoding));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return builder.build().toUriString();
	}

	/**
	 * 对url进行分步编码
	 * 
	 * @param url
	 * @param encodePath
	 * @param encodeQuery
	 * @param encodeRef
	 * @return
	 */
	public static String encodeURL(String url, boolean encodePath,
			boolean encodeQuery, boolean encodeRef) {
		return encodeURL(url, encodePath, encodeQuery, encodeRef,
				Charsets.UTF_8.name());
	}

	/**
	 * 对url进行解码
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 */
	public static String decodeURL(String url, String encoding) {
		try {
			return UriUtils.decode(url, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对url进行解码
	 * 
	 * @param url
	 * @return
	 */
	public static String decodeURL(String url) {
		return decodeURL(url, Charsets.UTF_8.name());
	}

	/**
	 * 去除参数
	 * 
	 * @param url
	 * @return
	 */
	public static String stripQuery(String url) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		builder.replaceQuery(null);
		return builder.build().toUriString();
	}

	/**
	 * 去除锚点
	 * 
	 * @param url
	 * @return
	 */
	public static String stripRef(String url) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		builder.fragment(null);
		return builder.build().toUriString();
	}

	/**
	 * 精简参数列表
	 * 
	 * @param url
	 * @param params
	 * @param reserved
	 * @return
	 */
	public static String stripQueryParam(String url, Set<String> params,
			boolean reserved) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		UriComponents components = builder.build();
		builder.replaceQuery(null);

		MultiValueMap<String, String> paramKVs = components.getQueryParams();
		String paramName = null;
		List<String> paramValue = null;
		for (Map.Entry<String, List<String>> entry : paramKVs.entrySet()) {
			paramName = entry.getKey();
			paramValue = entry.getValue();

			if (!(reserved ^ params.contains(paramName))) {
				if (CollectionUtils.isEmpty(paramValue))
					builder.queryParam(paramName, (Object[]) null);
				else
					builder.queryParam(paramName, paramValue.toArray());
			}

		}

		return builder.build().toUriString();
	}
}
