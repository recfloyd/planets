package org.rec.planets.jupiter.processor.workflow.conditioned;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.processor.Action;

/**
 * 执行while-do循环的处理器
 * @author rec
 *
 */
public class WhiledoProcessor extends AbstractConditionedProcessor {
	private Action nestedProcessor;

	@Override
	public void execute(ActionContext crawlContext) throws Exception {
		while (eval(crawlContext))
			nestedProcessor.execute(crawlContext);
	}

	public void setNestedProcessor(Action nestedProcessor) {
		this.nestedProcessor = nestedProcessor;
	}
}
