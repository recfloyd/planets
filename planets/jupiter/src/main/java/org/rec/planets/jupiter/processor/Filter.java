package org.rec.planets.jupiter.processor;

import org.rec.planets.jupiter.bean.CrawlContext;

public interface Filter {
	void invoke(CrawlProcessor processor,CrawlContext crawlContext) throws Exception;
}
