package org.rec.planets.jupiter.action.workflow.conditioned;

import java.util.List;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

public class ForbreakAction extends AbstractConditionedAction {
	private List<Action> actions;

	@Override
	public void execute(ActionContext context) throws Exception {
		for (Action action : actions) {
			action.execute(context);
			if (eval(context))
				break;
		}

	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
}
