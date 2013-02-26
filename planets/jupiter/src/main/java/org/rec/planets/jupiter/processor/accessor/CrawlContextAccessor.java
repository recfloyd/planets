package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;

public interface CrawlContextAccessor {
	Object get(CrawlContext crawlContext, String key);

	void set(CrawlContext crawlContext, String key, Object item);
}
