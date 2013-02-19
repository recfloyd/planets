package org.rec.planets.mercury.url.processor.recognize.page.bean;

import org.rec.planets.mercury.parse.bean.OrderedRegex;

public class PageTypeOrderedRegex extends OrderedRegex {
	private byte pageType;

	public PageTypeOrderedRegex(String expression, int[] groups, int flag,
			byte order, byte pageType) {
		super(expression, groups, flag, order);
		this.pageType = pageType;
	}

	public PageTypeOrderedRegex(String expression, int[] groups, byte order,
			byte pageType) {
		super(expression, groups, order);
		this.pageType = pageType;
	}

	public PageTypeOrderedRegex(String expression, byte order, byte pageType) {
		super(expression, order);
		this.pageType = pageType;
	}

	public byte getPageType() {
		return pageType;
	}

}
