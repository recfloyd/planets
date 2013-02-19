package org.rec.planets.jupiter.processor;

import java.util.List;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainedCrawlProcessor implements CrawlProcessor {
	private static final Logger logger = LoggerFactory
			.getLogger(ChainedCrawlProcessor.class);
	private List<CrawlProcessor> processors;
	private boolean omitException;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		for (CrawlProcessor processor : processors) {
			try {
				processor.process(crawlContext);
			} catch (Exception e) {
				if (omitException) {
					logger.warn(
							"error occured and omitted when process crawlURL: "
									+ crawlContext.getCrawlURL(), e);
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

}
