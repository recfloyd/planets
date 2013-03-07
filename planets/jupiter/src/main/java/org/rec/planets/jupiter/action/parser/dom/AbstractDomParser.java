package org.rec.planets.jupiter.action.parser.dom;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.action.parser.dom.bean.DomElement;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Strings;

/**
 * 抽象的dom解析器
 * 
 * @author rec
 * 
 */
public abstract class AbstractDomParser extends AbstractReadProcessWriteAction {
	protected DomElement domElement;

	public void setDomElement(DomElement domElement) {
		this.domElement = domElement;
	}

	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		Document document = (Document) source;
		Elements elements = document.select(domElement.getSelector());
		if (CollectionUtils.isEmpty(elements))
			return null;
		else
			return extract(elements, context);
	}

	protected abstract Object extract(Elements elements,
			ActionContext context);

	protected String extractFromElement(Element element) {
		if (Strings.isNullOrEmpty(domElement.getAttrabute())) {
			return element.html().trim();
		} else {
			return element.attr(domElement.getAttrabute()).trim();
		}
	}
}
