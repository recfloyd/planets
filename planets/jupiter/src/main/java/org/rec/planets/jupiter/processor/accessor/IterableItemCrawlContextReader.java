package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.workflow.iterable.bean.IterableItemStackHolder;

/**
 * 循环读取器 用在Iterable循环内 读取的时候从当前线程的ThreadLocal读取当前循环内容
 * 
 * @author rec
 * 
 */
public class IterableItemCrawlContextReader implements CrawlContextReader {

	@Override
	public Object get(CrawlContext crawlContext, String key) {
		return IterableItemStackHolder.getItem().getTarget();
	}
}
