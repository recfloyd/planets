package org.rec.planets.mercury.parse.bean;

public class OrderedRegex extends Regex implements Comparable<OrderedRegex> {
	private static final long serialVersionUID = 5489386567832218213L;
	private byte order;

	public OrderedRegex(String expression, int[] groups, int flag,
			boolean strict, byte order) {
		super(expression, groups, flag, strict);
		this.order = order;
	}

	public OrderedRegex(String expression, int[] groups, int flag, byte order) {
		super(expression, groups, flag);
		this.order = order;
	}

	public OrderedRegex(String expression, int[] groups, byte order) {
		super(expression, groups);
		this.order = order;
	}

	public OrderedRegex(String expression, boolean strict, byte order) {
		super(expression, strict);
		this.order = order;
	}

	public OrderedRegex(String expression, byte order) {
		super(expression);
		this.order = order;
	}

	public byte getOrder() {
		return order;
	}

	@Override
	public int compareTo(OrderedRegex o) {
		return this.order - o.order;
	}

}
