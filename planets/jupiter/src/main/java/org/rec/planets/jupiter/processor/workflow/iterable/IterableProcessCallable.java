package org.rec.planets.jupiter.processor.workflow.iterable;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.workflow.ProcessCallable;

/**
 * 并行循环处理线程
 * @author rec
 *
 */
public class IterableProcessCallable extends ProcessCallable {
	private IterableItem item;

	public IterableProcessCallable(CrawlProcessor processor,
			CrawlContext crawlContext, IterableItem item) {
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
