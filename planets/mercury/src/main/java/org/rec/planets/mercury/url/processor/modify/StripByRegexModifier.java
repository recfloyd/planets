package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.bean.Regex;

/**
 * 根据正则进行筛选重组
 * @author rec
 *
 */
public class StripByRegexModifier extends AbstractStripModifier {
	private static final long serialVersionUID = -2722968695080374437L;
	private Regex regex;

	@Override
	protected String modify(String url, String baseURL) {
		return super.extractFromRegex(url, regex);
	}

	public void setRegex(Regex regex) {
		this.regex = regex;
	}
}
