package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;

/**
 * crawlContext写入器
 * @author rec
 *
 */
public interface CrawlContextWriter {
	void set(CrawlContext crawlContext,String key, Object result);
}
