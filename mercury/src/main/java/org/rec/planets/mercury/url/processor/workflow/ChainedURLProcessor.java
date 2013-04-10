package org.rec.planets.mercury.url.processor.workflow;

import java.util.List;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 链式处理器
 * 
 * @author rec
 * 
 */
public class ChainedURLProcessor extends AbstractBean implements URLProcessor {
	private static final long serialVersionUID = 3506281556057687467L;
	private static final Logger logger = LoggerFactory
			.getLogger(ChainedURLProcessor.class);
	private List<URLProcessor> processors;
	private boolean omitException;

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		for (URLProcessor processor : processors) {
			try {
				processor.process(crawlURL, baseURL);
			} catch (Exception e) {
				if (omitException) {
					logger.warn(
							"error occured and omitted when process crawlURL: "
									+ crawlURL, e);
					continue;
				} else {
					throw e;
				}
			}
		}
	}

	public void setProcessors(List<URLProcessor> processors) {
		this.processors = processors;
	}

	public void setOmitException(boolean omitException) {
		this.omitException = omitException;
	}
}
