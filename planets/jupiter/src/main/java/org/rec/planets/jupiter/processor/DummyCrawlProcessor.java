package org.rec.planets.jupiter.processor;

import org.rec.planets.jupiter.bean.CrawlContext;

public class DummyCrawlProcessor implements CrawlProcessor {
	private static volatile DummyCrawlProcessor instance;

	public static DummyCrawlProcessor getInstance() {
		if (instance == null) {
			synchronized (DummyCrawlProcessor.class) {
				if (instance == null)
					instance = new DummyCrawlProcessor();
			}
		}
		return instance;
	}

	private DummyCrawlProcessor() {
	}

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		// do nothing
	}

}
