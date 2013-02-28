package org.rec.planets.jupiter.processor.parse.dom;

import org.jsoup.select.Elements;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 只取domElement第一个目标的dom解析器,匹配结果是一个String
 * 
 * @author rec
 * 
 */
public class FirstMatchDomParser extends AbstractDomParser {

	@Override
	protected Object extract(Elements elements, ActionContext context) {
		return super.extractFromElement(elements.first());
	}

}
