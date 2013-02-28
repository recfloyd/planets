package org.rec.planets.jupiter.processor.workflow.conditioned;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;
import org.rec.planets.jupiter.processor.Action;

/**
 * 基于条件判断的处理器
 * 
 * @author rec
 * 
 */
public abstract class AbstractConditionedProcessor implements Action {
	private ContextReader crawlContextReader;
	private String evalString;

	protected boolean eval(ActionContext crawlContext) {
		Boolean result = (Boolean) crawlContextReader.read(crawlContext,
				evalString);
		return result == null ? false : result;
	}

	public void setCrawlContextReader(ContextReader crawlContextReader) {
		this.crawlContextReader = crawlContextReader;
	}

	public void setEvalString(String evalString) {
		this.evalString = evalString;
	}

}
