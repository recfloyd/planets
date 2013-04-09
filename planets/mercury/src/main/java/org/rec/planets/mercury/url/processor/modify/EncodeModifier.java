package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.URLUtil;

import com.google.common.base.Strings;

/**
 * 对url进行encoding操作
 * 
 * @author rec
 * 
 */
public class EncodeModifier extends AbstractModifier {
	private static final long serialVersionUID = -963870389762781758L;
	private String encoding;
	private boolean encodePath;
	private boolean encodeQuery;
	private boolean encodeRef;

	@Override
	public String modify(String url, String baseURL) {
		if (Strings.isNullOrEmpty(encoding))
			return URLUtil.encodeURL(url, encodePath, encodeQuery, encodeRef);
		else
			return URLUtil.encodeURL(url, encodePath, encodeQuery, encodeRef,
					encoding);
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setEncodePath(boolean encodePath) {
		this.encodePath = encodePath;
	}

	public void setEncodeQuery(boolean encodeQuery) {
		this.encodeQuery = encodeQuery;
	}

	public void setEncodeRef(boolean encodeRef) {
		this.encodeRef = encodeRef;
	}
}
