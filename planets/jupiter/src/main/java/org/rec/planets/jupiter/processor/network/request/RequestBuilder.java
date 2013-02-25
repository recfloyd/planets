package org.rec.planets.jupiter.processor.network.request;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.network.bean.Request;

/**
 * 请求实体创建器
 * @author rec
 *
 */
public interface RequestBuilder {
	Request build(CrawlContext crawlContext) throws Exception;
}
