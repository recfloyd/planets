package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;

/**
 * crawlContext读取器
 * 
 * @author rec
 * 
 */
public interface CrawlContextReader {
	Object get(CrawlContext crawlContext,String key);
}
