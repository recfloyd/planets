package org.rec.planets.jupiter.processor;

import org.rec.planets.jupiter.bean.CrawlContext;

/**
 * 处理器
 * @author rec
 *
 */
public interface CrawlProcessor {
	void process(CrawlContext crawlContext) throws Exception;
}
