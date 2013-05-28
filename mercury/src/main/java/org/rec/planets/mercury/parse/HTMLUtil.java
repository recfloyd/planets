package org.rec.planets.mercury.parse;

import org.springframework.web.util.HtmlUtils;

public abstract class HTMLUtil {
	public static final String regex = "</?[a-z][a-z0-9]*[^<>]*>|<!--.*?-->";

	public static String removeHTMLTag(String source) {
		if (source == null)
			return null;
		String tmp = HtmlUtils.htmlUnescape(source);
		if (tmp == null)
			return null;
		return RegexUtil.replaceAll(tmp, regex, "").trim();
	}
}
