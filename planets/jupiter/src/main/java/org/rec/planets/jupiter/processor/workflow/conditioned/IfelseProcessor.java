package org.rec.planets.jupiter.processor.workflow.conditioned;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.processor.Action;

/**
 * if-else处理器,分别注入2个处理器,在判断为if和else的时候执行各自的处理
 * @author rec
 *
 */
public class IfelseProcessor extends AbstractConditionedProcessor {
	private Action ifProcessor;
	private Action elseProcessor;

	@Override
	public void execute(ActionContext crawlContext) throws Exception {
		if (eval(crawlContext)) {
			if (ifProcessor != null)
				ifProcessor.execute(crawlContext);
		} else {
			if (elseProcessor != null)
				elseProcessor.execute(crawlContext);
		}

	}

	public Action getIfProcessor() {
		return ifProcessor;
	}

	public void setIfProcessor(Action ifProcessor) {
		this.ifProcessor = ifProcessor;
	}

	public Action getElseProcessor() {
		return elseProcessor;
	}

	public void setElseProcessor(Action elseProcessor) {
		this.elseProcessor = elseProcessor;
	}
}
