package org.rec.planets.mercury.parse.bean;

import org.rec.planets.mercury.domain.AbstractBean;

import jregex.REFlags;

import com.google.common.base.Objects;

/**
 * 正则表达式
 * 
 * @author rec
 * 
 */
public class Regex extends AbstractBean {
	private String expression;// 表达式
	private int[] groups;// 捕获组
	private int flag = REFlags.IGNORE_CASE;

	public Regex(String expression, int[] groups, int flag) {
		this.expression = expression;
		this.groups = groups;
		this.flag = flag;
	}

	public Regex(String expression, int[] groups) {
		this(expression, groups, REFlags.IGNORE_CASE);
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

	@Override
	public int hashCode() {
		return Objects.hashCode(expression, groups, flag);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Regex))
			return false;

		Regex r = (Regex) obj;
		return Objects.equal(expression, r.expression)
				&& Objects.equal(groups, r.groups)
				&& Objects.equal(flag, r.flag);
	}

}
