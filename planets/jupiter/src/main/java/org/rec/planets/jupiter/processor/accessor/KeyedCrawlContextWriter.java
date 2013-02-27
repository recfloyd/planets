package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 基于key的写入器
 * 
 * @author rec
 * 
 */
public class KeyedCrawlContextWriter implements CrawlContextWriter {

	@Override
	public void set(CrawlContext crawlContext, String key, Object result) {
		if (result != null) {
			BeanWrapper bw = new BeanWrapperImpl(crawlContext);
			bw.setPropertyValue(key, result);
		}

	}
}
