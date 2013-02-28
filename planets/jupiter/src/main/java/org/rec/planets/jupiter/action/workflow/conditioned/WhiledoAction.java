package org.rec.planets.jupiter.action.workflow.conditioned;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 执行while-do循环的处理器
 * 
 * @author rec
 * 
 */
public class WhiledoAction extends AbstractConditionedAction {
	private Action nestedAction;

	@Override
	public void execute(ActionContext context) throws Exception {
		while (eval(context))
			nestedAction.execute(context);
	}

	public void setNestedAction(Action nestedAction) {
		this.nestedAction = nestedAction;
	}
}
