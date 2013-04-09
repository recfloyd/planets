package org.rec.planets.mercury.url.processor.recognize.page.bean;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.parse.bean.OrderedRegex;

public class PageTypeIndicator extends AbstractBean implements
		Comparable<PageTypeIndicator> {
	private static final long serialVersionUID = 3279551713327808146L;
	private byte pageType;
	private boolean omitHost;
	private OrderedRegex regex;

	public PageTypeIndicator(byte pageType, boolean omitHost, OrderedRegex regex) {
		this.pageType = pageType;
		this.omitHost = omitHost;
		this.regex = regex;
	}

	public PageTypeIndicator(byte pageType, OrderedRegex regex) {
		this(pageType, false, regex);
	}

	public byte getPageType() {
		return pageType;
	}

	public boolean isOmitHost() {
		return omitHost;
	}

	public OrderedRegex getRegex() {
		return regex;
	}

	@Override
	public int compareTo(PageTypeIndicator o) {
		return this.regex.compareTo(o.regex);
	}
}
