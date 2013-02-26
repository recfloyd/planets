package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.workflow.iterable.IterableItemStackHolder;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class ReadOriginWriteIndexAccessor extends BasicAccessor {
	@Override
	public void set(CrawlContext crawlContext, String key, Object item) {
		String newKey = key + '['
				+ IterableItemStackHolder.getItem().getIndex() + ']';
		BeanWrapper bw = new BeanWrapperImpl(crawlContext);
		bw.setPropertyValue(newKey, item);
	}
}
