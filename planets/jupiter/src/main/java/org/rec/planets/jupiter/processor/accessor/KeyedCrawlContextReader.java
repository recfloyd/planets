package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.mercury.expression.ELUtil;

/**
 * 基于键值的读取器
 * 
 * @author rec
 * 
 */
public class KeyedCrawlContextReader implements CrawlContextReader {
	@Override
	public Object get(CrawlContext crawlContext, String key) {
		return ELUtil.evalFromObject(crawlContext, key);
	}
}
