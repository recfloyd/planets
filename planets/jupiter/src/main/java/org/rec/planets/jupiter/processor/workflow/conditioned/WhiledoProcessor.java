package org.rec.planets.jupiter.processor.workflow.conditioned;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;

/**
 * 执行while-do循环的处理器
 * @author rec
 *
 */
public class WhiledoProcessor extends AbstractConditionedProcessor {
	private CrawlProcessor nestedProcessor;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		while (eval(crawlContext))
			nestedProcessor.process(crawlContext);
	}

	public void setNestedProcessor(CrawlProcessor nestedProcessor) {
		this.nestedProcessor = nestedProcessor;
	}
}
