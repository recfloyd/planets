package org.rec.planets.jupiter.processor.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 并行处理器.它将一组可并行运行的处理器运行在一个线程池上,并等待所有处理结束之后再返回
 * 
 * @author rec
 * 
 */
public class ParallelCrawlProcessor implements CrawlProcessor {
	private static final Logger logger = LoggerFactory
			.getLogger(ParallelCrawlProcessor.class);
	private List<CrawlProcessor> processors;
	private boolean omitException;
	private ExecutorService threadPool;

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
