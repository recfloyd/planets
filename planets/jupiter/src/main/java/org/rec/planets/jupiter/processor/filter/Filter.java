package org.rec.planets.jupiter.processor.filter;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;

public interface Filter {
	void invoke(CrawlProcessor processor,CrawlContext crawlContext) throws Exception;
}
