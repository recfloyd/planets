package org.rec.planets.jupiter.processor.parse.dom;

import org.jsoup.Jsoup;
import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.parse.AbstractParser;

/**
 * DOM对象准备器
 * @author rec
 *
 */
public class DomPrepareParser extends AbstractParser {
	@Override
	protected Object parseInternal(CrawlContext crawlContext, Object source)
			throws Exception {
		String html = (String) source;
		return Jsoup.parse(html);
	}
}
