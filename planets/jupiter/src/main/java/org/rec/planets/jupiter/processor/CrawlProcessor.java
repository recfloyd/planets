package org.rec.planets.jupiter.processor;

import org.rec.planets.jupiter.bean.CrawlContext;

public interface CrawlProcessor {
	void process(CrawlContext crawlContext) throws Exception;
}
