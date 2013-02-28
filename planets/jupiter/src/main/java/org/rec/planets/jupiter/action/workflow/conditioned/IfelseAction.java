package org.rec.planets.jupiter.action.workflow.conditioned;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * if-else处理器,分别注入2个处理器,在判断为if和else的时候执行各自的处理
 * @author rec
 *
 */
public class IfelseAction extends AbstractConditionedAction {
	private Action ifAction;
	private Action elseAction;

	@Override
	public void execute(ActionContext context) throws Exception {
		if (eval(context)) {
			if (ifAction != null)
				ifAction.execute(context);
		} else {
			if (elseAction != null)
				elseAction.execute(context);
		}

	}

	public void setIfAction(Action ifAction) {
		this.ifAction = ifAction;
	}

	public void setElseAction(Action elseAction) {
		this.elseAction = elseAction;
	}
}
