package org.rec.planets.jupiter.processor.parse.dom;

import org.jsoup.Jsoup;
import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.AbstractReadWriteProcessor;

/**
 * DOM对象准备器
 * 
 * @author rec
 * 
 */
public class DomPrepareParser extends AbstractReadWriteProcessor {
	@Override
	protected Object processInternal(CrawlContext crawlContext, Object source)
			throws Exception {
		String html = (String) source;
		return Jsoup.parse(html);
	}
}
