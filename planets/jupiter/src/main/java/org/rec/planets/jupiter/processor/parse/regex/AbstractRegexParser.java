package org.rec.planets.jupiter.processor.parse.regex;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.parse.AbstractParser;
import org.rec.planets.mercury.parse.bean.Regex;

/**
 * 抽象正则解析器
 * @author rec
 *
 */
public abstract class AbstractRegexParser extends AbstractParser {
	protected Regex regex;
	protected boolean strictRegex;

	@Override
	protected Object parseInternal(CrawlContext crawlContext, Object source)
			throws Exception {
		String text = (String) source;
		return find(text);
	}

	protected abstract Object find(String text);

	public void setRegex(Regex regex) {
		this.regex = regex;
	}

	public void setStrictRegex(boolean strictRegex) {
		this.strictRegex = strictRegex;
	}
}
