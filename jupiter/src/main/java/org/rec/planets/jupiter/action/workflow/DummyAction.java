package org.rec.planets.jupiter.action.workflow;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 空处理器,什么也不做
 * @author rec
 *
 */
public class DummyAction implements Action {
	private static volatile DummyAction instance;

	public static DummyAction getInstance() {
		if (instance == null) {
			synchronized (DummyAction.class) {
				if (instance == null)
					instance = new DummyAction();
			}
		}
		return instance;
	}

	private DummyAction() {
	}

	@Override
	public void execute(ActionContext context) throws Exception {
		// do nothing
	}

}
