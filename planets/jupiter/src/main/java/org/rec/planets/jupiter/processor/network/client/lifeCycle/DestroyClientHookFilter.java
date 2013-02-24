package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.bean.CrawlContextConstants;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.Filter;
import org.rec.planets.jupiter.processor.network.client.Client;

public class DestroyClientHookFilter implements Filter {
	private DestroyClientHookFilter() {
	}

	private static volatile DestroyClientHookFilter instance;

	public static DestroyClientHookFilter getInstance() {
		if (instance == null) {
			synchronized (DestroyClientHookFilter.class) {
				if (instance == null) {
					instance = new DestroyClientHookFilter();
				}
			}
		}
		return instance;
	}

	@Override
	public void invoke(CrawlProcessor processor, CrawlContext crawlContext)
			throws Exception {
		try {
			processor.process(crawlContext);
		} finally {
			Client client = (Client) (crawlContext.getContext()
					.get(CrawlContextConstants.KEY_CLIENT));
			if (client != null)
				client.destroy();
		}
	}
}
