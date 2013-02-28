package org.rec.planets.jupiter.action.parser.dom;

import org.jsoup.Jsoup;
import org.rec.planets.jupiter.action.AbstractReadWriteAction;
import org.rec.planets.jupiter.context.ActionContext;

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
