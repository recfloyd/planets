package org.rec.planets.jupiter.processor.parse.dom;

import org.jsoup.select.Elements;
import org.rec.planets.jupiter.bean.CrawlContext;

/**
 * 只取domElement第一个目标的dom解析器,匹配结果是一个String
 * 
 * @author rec
 * 
 */
public class FirstMatchDomParser extends AbstractDomParser {

	@Override
	protected Object extract(Elements elements, CrawlContext crawlContext) {
		return super.extractFromElement(elements.first());
	}

}
