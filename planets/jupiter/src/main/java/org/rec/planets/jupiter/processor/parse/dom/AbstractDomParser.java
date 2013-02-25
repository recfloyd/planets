package org.rec.planets.jupiter.processor.parse.dom;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.parse.AbstractParser;
import org.rec.planets.jupiter.processor.parse.dom.bean.DomElement;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Strings;

/**
 * 抽象的dom解析器
 * @author rec
 *
 */
public abstract class AbstractDomParser extends AbstractParser {
	protected DomElement domElement;

	public void setDomElement(DomElement domElement) {
		this.domElement = domElement;
	}

	@Override
	protected Object parseInternal(CrawlContext crawlContext, Object source)
			throws Exception {
		Document document = (Document) source;
		Elements elements = document.select(domElement.getSelector());
		if (CollectionUtils.isEmpty(elements))
			return null;
		else
			return extract(elements, crawlContext);
	}

	protected abstract Object extract(Elements elements,
			CrawlContext crawlContext);
	
	protected String extractFromElement(Element element) {
		if (Strings.isNullOrEmpty(domElement.getAttrabute())) {
			return element.html().trim();
		} else {
			return element.attr(domElement.getAttrabute()).trim();
		}
	}
}
