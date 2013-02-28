package org.rec.planets.jupiter.processor.workflow;

import java.util.concurrent.Callable;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.processor.Action;

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
