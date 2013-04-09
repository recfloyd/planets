package org.rec.planets.mercury.parse.bean;

import jregex.REFlags;

import org.rec.planets.mercury.domain.AbstractBean;

import com.google.common.base.Objects;

/**
 * 正则表达式
 * 
 * @author rec
 * 
 */
public class Regex extends AbstractBean {
	private static final long serialVersionUID = -6265301024511630364L;
	private String expression;// 表达式
	private int[] groups;// 捕获组
	private int flag;
	private boolean strict;

	public Regex(String expression, int[] groups, int flag, boolean strict) {
		this.expression = expression;
		this.groups = groups;
		this.flag = flag;
		this.strict = strict;
	}

	public Regex(String expression, int[] groups, int flag) {
		this(expression, groups, flag, true);
	}

	public Regex(String expression, int[] groups) {
		this(expression, groups, REFlags.IGNORE_CASE);
	}

	public Regex(String expression, boolean strict) {
		this(expression, null, REFlags.IGNORE_CASE, strict);
	}

	public Regex(String expression) {
		this(expression, null, REFlags.IGNORE_CASE);
	}

	public String getExpression() {
		return expression;
	}

	public int[] getGroups() {
		return groups;
	}

	public int getFlag() {
		return flag;
	}

	public boolean isStrict() {
		return strict;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(expression, flag);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Regex))
			return false;

		Regex r = (Regex) obj;
		return Objects.equal(expression, r.expression)
				&& Objects.equal(flag, r.flag);
	}

}
