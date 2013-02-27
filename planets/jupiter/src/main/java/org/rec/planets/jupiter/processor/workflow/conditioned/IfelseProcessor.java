package org.rec.planets.jupiter.processor.workflow.conditioned;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;

/**
 * if-else处理器,分别注入2个处理器,在判断为if和else的时候执行各自的处理
 * @author rec
 *
 */
public class IfelseProcessor extends AbstractConditionedProcessor {
	private CrawlProcessor ifProcessor;
	private CrawlProcessor elseProcessor;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		if (eval(crawlContext)) {
			if (ifProcessor != null)
				ifProcessor.process(crawlContext);
		} else {
			if (elseProcessor != null)
				elseProcessor.process(crawlContext);
		}

	}

	public CrawlProcessor getIfProcessor() {
		return ifProcessor;
	}

	public void setIfProcessor(CrawlProcessor ifProcessor) {
		this.ifProcessor = ifProcessor;
	}

	public CrawlProcessor getElseProcessor() {
		return elseProcessor;
	}

	public void setElseProcessor(CrawlProcessor elseProcessor) {
		this.elseProcessor = elseProcessor;
	}
}
