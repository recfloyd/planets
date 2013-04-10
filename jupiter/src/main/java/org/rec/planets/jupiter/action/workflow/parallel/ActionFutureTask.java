package org.rec.planets.jupiter.action.workflow.parallel;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

public class ActionFutureTask extends FutureTask<Void> {
	public ActionFutureTask(final Action action, final ActionContext context) {
		super(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				action.execute(context);
				return null;
			}
		});
	}
}
