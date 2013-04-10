package org.rec.planets.mercury.url.processor.recognize.fingerprint.eigen;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.parse.bean.OrderedRegex;

public class EigenRegex extends AbstractBean implements Comparable<EigenRegex> {
	private static final long serialVersionUID = -549551302993155357L;
	private boolean omitHost;
	private OrderedRegex regex;

	public EigenRegex(boolean omitHost, OrderedRegex regex) {
		this.omitHost = omitHost;
		this.regex = regex;
	}

	public EigenRegex(OrderedRegex regex) {
		this(false, regex);
	}

	public boolean isOmitHost() {
		return omitHost;
	}

	public OrderedRegex getRegex() {
		return regex;
	}

	@Override
	public int compareTo(EigenRegex o) {
		return this.regex.compareTo(o.regex);
	}

}
