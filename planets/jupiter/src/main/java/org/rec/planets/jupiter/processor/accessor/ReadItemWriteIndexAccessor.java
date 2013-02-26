package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.workflow.iterable.IterableItemStackHolder;

public final class ReadItemWriteIndexAccessor extends
		ReadOriginWriteIndexAccessor {

	@Override
	public Object get(CrawlContext crawlContext, String key) {
		return IterableItemStackHolder.getItem().getTarget();
	}
}
