package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.workflow.iterable.bean.IterableItemStackHolder;

/**
 * 循环读写器 用在Iterable循环内 读取的时候从当前线程的ThreadLocal读取当前循环内容 写入的时候加一个index编号
 * 
 * @author rec
 * 
 */
public final class ReadItemWriteIndexAccessor extends
		ReadOriginWriteIndexAccessor {

	@Override
	public Object get(CrawlContext crawlContext) {
		return IterableItemStackHolder.getItem().getTarget();
	}
}
