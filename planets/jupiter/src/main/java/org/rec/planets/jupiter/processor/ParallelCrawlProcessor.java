package org.rec.planets.jupiter.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParallelCrawlProcessor implements CrawlProcessor {
	private static final Logger logger = LoggerFactory
			.getLogger(ParallelCrawlProcessor.class);
	private List<CrawlProcessor> processors;
	private boolean omitException;
	private ExecutorService threadPool;

	private static class ProcessCallable implements Callable<Void> {
		private CrawlProcessor processor;
		private CrawlContext crawlContext;

		private ProcessCallable(CrawlProcessor processor,
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

	@Override
	public void process(final CrawlContext crawlContext) throws Exception {
		List<ProcessCallable> tasks = new ArrayList<ProcessCallable>(
				processors.size());
		for (CrawlProcessor processor : processors) {
			tasks.add(new ProcessCallable(processor, crawlContext));
		}

		List<Future<Void>> reaults = threadPool.invokeAll(tasks);
		for (int i = 0; i < reaults.size(); i++) {
			try {
				reaults.get(i);
			} catch (Exception e) {
				if (omitException) {
					logger.warn(
							"error occured and omitted when process crawlURL: "
									+ crawlContext.getCrawlURL()
									+ " and processor index is " + i
									+ " and processor is " + processors.get(i),
							e);
					continue;
				} else {
					throw e;
				}
			}
		}
	}

	public void setProcessors(List<CrawlProcessor> processors) {
		this.processors = processors;
	}

	public void setOmitException(boolean omitException) {
		this.omitException = omitException;
	}

	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}
}
