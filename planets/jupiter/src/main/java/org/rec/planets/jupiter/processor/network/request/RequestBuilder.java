package org.rec.planets.jupiter.processor.network.request;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.network.bean.Request;

public interface RequestBuilder {
	Request build(CrawlContext crawlContext) throws Exception;
}
