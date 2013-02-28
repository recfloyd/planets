package org.rec.planets.jupiter.processor.parse.dom;

import org.jsoup.Jsoup;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.processor.AbstractReadWriteAction;

/**
 * DOM对象准备器
 * 
 * @author rec
 * 
 */
public class DomPrepareParser extends AbstractReadWriteAction {
	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		String html = (String) source;
		return Jsoup.parse(html);
	}
}
