package org.rec.planets.jupiter.action.workflow.conditioned;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.reader.ContextReader;

/**
 * 基于条件判断的处理器
 * 
 * @author rec
 * 
 */
public abstract class AbstractConditionedAction implements Action {
	private ContextReader contextReader;
	private String evalString;

	protected boolean eval(ActionContext context) {
		Boolean result = (Boolean) contextReader.read(context, evalString);
		return result == null ? false : result;
	}

	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

	public void setEvalString(String evalString) {
		this.evalString = evalString;
	}

}
