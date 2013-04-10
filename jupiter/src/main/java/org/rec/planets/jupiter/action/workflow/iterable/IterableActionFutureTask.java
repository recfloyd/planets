package org.rec.planets.jupiter.action.workflow.iterable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.workflow.iterable.bean.IterableItem;
import org.rec.planets.jupiter.action.workflow.iterable.bean.IterableItemStackHolder;
import org.rec.planets.jupiter.context.ActionContext;

public class IterableActionFutureTask extends FutureTask<Void> {
	public IterableActionFutureTask(final Action action,
			final ActionContext context, final IterableItem item) {
		super(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				IterableItemStackHolder.putItem(item);
				try {
					action.execute(context);
					return null;
				} finally {
					IterableItemStackHolder.popItem();
				}
			}
		});
	}
}
