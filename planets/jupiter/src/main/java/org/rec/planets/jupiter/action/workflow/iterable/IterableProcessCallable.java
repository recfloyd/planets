package org.rec.planets.jupiter.action.workflow.iterable;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.workflow.ProcessCallable;
import org.rec.planets.jupiter.action.workflow.iterable.bean.IterableItem;
import org.rec.planets.jupiter.action.workflow.iterable.bean.IterableItemStackHolder;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 并行循环处理线程
 * @author rec
 *
 */
public class IterableProcessCallable extends ProcessCallable {
	private IterableItem item;

	public IterableProcessCallable(Action action,
			ActionContext context, IterableItem item) {
		super(action, context);
		this.item = item;
	}

	@Override
	public Void call() throws Exception {
		IterableItemStackHolder.putItem(item);
		try {
			return super.call();
		} finally {
			IterableItemStackHolder.popItem();
		}
	}
}
