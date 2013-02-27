package org.rec.planets.mercury.url.processor.workflow;

import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;

/**
 * 什么也不做的处理器,用于占位
 * 
 * @author rec
 * 
 */
public final class DummyURLProcessor implements URLProcessor {
	private static volatile DummyURLProcessor instance;

	public static DummyURLProcessor getInstance() {
		if (instance == null) {
			synchronized (DummyURLProcessor.class) {
				if (instance == null)
					instance = new DummyURLProcessor();
			}
		}
		return instance;
	}

	private DummyURLProcessor() {
	}

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		//do nothing
	}
}
