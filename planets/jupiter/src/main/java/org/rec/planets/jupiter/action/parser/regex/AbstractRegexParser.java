package org.rec.planets.jupiter.action.parser.regex;

import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.mercury.parse.bean.Regex;

/**
 * 抽象正则解析器
 * 
 * @author rec
 * 
 */
public abstract class AbstractRegexParser extends AbstractReadProcessWriteAction {
	protected Regex regex;
	protected boolean strictRegex;

	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		String text = (String) source;
		return find(text);
	}

	protected abstract Object find(String text);

	public void setRegex(Regex regex) {
		this.regex = regex;
	}

	public void setStrictRegex(boolean strictRegex) {
		this.strictRegex = strictRegex;
	}
}
