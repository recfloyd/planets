package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.workflow.iterable.bean.IterableItemStackHolder;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 循环写入器 用在Iterable循环内 向CrawlContext写入的时候默认加一个编号,这个编号从当前线程的ThreadLocal里面取
 * 
 * @author rec
 * 
 */
public class ReadOriginWriteIndexAccessor extends
		BasicKeyedCrawlContextAccessor {
	@Override
	public void set(CrawlContext crawlContext, Object result) {
		String newKey = resultKey + '['
				+ IterableItemStackHolder.getItem().getIndex() + ']';
		BeanWrapper bw = new BeanWrapperImpl(crawlContext);
		bw.setPropertyValue(newKey, result);
	}
}
