package org.rec.planets.jupiter.processor.workflow.iterable;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.processor.Action;
import org.rec.planets.jupiter.processor.workflow.ProcessCallable;
import org.rec.planets.jupiter.processor.workflow.iterable.bean.IterableItem;
import org.rec.planets.jupiter.processor.workflow.iterable.bean.IterableItemStackHolder;

/**
 * 并行循环处理线程
 * @author rec
 *
 */
public class IterableProcessCallable extends ProcessCallable {
	private IterableItem item;

	public IterableProcessCallable(Action processor,
			ActionContext crawlContext, IterableItem item) {
		super(processor, crawlContext);
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
