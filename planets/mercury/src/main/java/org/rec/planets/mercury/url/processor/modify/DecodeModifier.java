package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.URLUtil;

import com.google.common.base.Strings;

/**
 * 对URL进行decode操作
 * 
 * @author rec
 * 
 */
public class DecodeModifier extends AbstractModifier {
	private String encoding;

	@Override
	public String modify(String url, String baseURL) {
		if (Strings.isNullOrEmpty(encoding))
			return URLUtil.decodeURL(url);
		else
			return URLUtil.decodeURL(url, encoding);
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
