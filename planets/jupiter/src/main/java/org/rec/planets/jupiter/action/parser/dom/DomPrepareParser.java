package org.rec.planets.jupiter.action.parser.dom;

import org.jsoup.Jsoup;
import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * DOM对象准备器
 * 
 * @author rec
 * 
 */
public class DomPrepareParser extends AbstractReadProcessWriteAction {
	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		String html = (String) source;
		return Jsoup.parse(html);
	}
}
