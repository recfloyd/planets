package org.rec.planets.jupiter.action.workflow;

import java.util.concurrent.Callable;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

public class ProcessCallable implements Callable<Void> {
	private Action action;
	private ActionContext context;

	public ProcessCallable(Action action,
			ActionContext context) {
		this.action = action;
		this.context = context;
	}

	@Override
	public Void call() throws Exception {
		action.execute(context);
		return null;
	}
}
