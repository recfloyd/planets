package org.rec.planets.jupiter.processor.workflow;

import java.util.concurrent.Callable;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;

public class ProcessCallable implements Callable<Void> {
	private CrawlProcessor processor;
	private CrawlContext crawlContext;

	public ProcessCallable(CrawlProcessor processor,
			CrawlContext crawlContext) {
		this.processor = processor;
		this.crawlContext = crawlContext;
	}

	@Override
	public Void call() throws Exception {
		processor.process(crawlContext);
		return null;
	}
}
