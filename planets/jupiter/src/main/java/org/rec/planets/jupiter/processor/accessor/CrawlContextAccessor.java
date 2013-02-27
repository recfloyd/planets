package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;

/**
 * CrawlContext读写器
 * 
 * @author rec
 * 
 */
public interface CrawlContextAccessor {
	Object get(CrawlContext crawlContext);
	void set(CrawlContext crawlContext, Object result);
}
