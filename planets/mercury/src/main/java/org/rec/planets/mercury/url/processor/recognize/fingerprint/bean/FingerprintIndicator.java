package org.rec.planets.mercury.url.processor.recognize.fingerprint.bean;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.parse.bean.OrderedRegex;

public class FingerprintIndicator extends AbstractBean implements
		Comparable<FingerprintIndicator> {
	private boolean omitHost;
	private OrderedRegex regex;

	public FingerprintIndicator(boolean omitHost, OrderedRegex regex) {
		this.omitHost = omitHost;
		this.regex = regex;
	}

	public FingerprintIndicator(OrderedRegex regex) {
		this(false, regex);
	}

	public boolean isOmitHost() {
		return omitHost;
	}

	public OrderedRegex getRegex() {
		return regex;
	}

	@Override
	public int compareTo(FingerprintIndicator o) {
		return this.regex.compareTo(o.regex);
	}

}
