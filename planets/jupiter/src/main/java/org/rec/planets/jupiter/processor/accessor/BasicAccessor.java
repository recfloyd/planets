package org.rec.planets.jupiter.processor.accessor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.mercury.expression.ELUtil;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 基础的CrawlContext读写器
 * @author rec
 *
 */
public class BasicAccessor implements CrawlContextAccessor {
	@Override
	public Object get(CrawlContext crawlContext, String key) {
		return ELUtil.evalFromObject(crawlContext, key);
	}

	@Override
	public void set(CrawlContext crawlContext, String key, Object item) {
		BeanWrapper bw = new BeanWrapperImpl(crawlContext);
		bw.setPropertyValue(key, item);
	}
}
