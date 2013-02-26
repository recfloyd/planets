package org.rec.planets.jupiter.processor.workflow;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;

/**
 * 空处理器,什么也不做
 * @author rec
 *
 */
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
