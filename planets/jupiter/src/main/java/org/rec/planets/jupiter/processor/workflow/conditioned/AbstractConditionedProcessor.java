package org.rec.planets.jupiter.processor.workflow.conditioned;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessable;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessor;

/**
 * 基于条件判断的处理器
 * @author rec
 *
 */
public abstract class AbstractConditionedProcessor implements CrawlContextAccessable, CrawlProcessor {
	protected CrawlContextAccessor crawlContextAccessor;
	
	protected boolean eval(CrawlContext crawlContext){
		Boolean result=(Boolean)crawlContextAccessor.get(crawlContext);
		return result;
	}

	@Override
	public void setCrawlContextAccessor(
			CrawlContextAccessor crawlContextAccessor) {
		this.crawlContextAccessor=crawlContextAccessor;
	}

}
