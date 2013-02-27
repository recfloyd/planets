package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.CrawlContextReader;
import org.rec.planets.jupiter.processor.filter.Filter;
import org.rec.planets.jupiter.processor.network.client.Client;

/**
 * http客户端销毁过滤器,执行完下载之后,此过滤器将客户端销毁
 * 
 * @author rec
 * 
 */
public class DestroyClientHookFilter implements Filter {
	private CrawlContextReader crawlContextReader;
	private String clientKey;

	@Override
	public void invoke(CrawlProcessor processor, CrawlContext crawlContext)
			throws Exception {
		try {
			processor.process(crawlContext);
		} finally {
			Client client = (Client) crawlContextReader.get(crawlContext,
					clientKey);
			if (client != null)
				client.destroy();
		}
	}

	public void setCrawlContextReader(CrawlContextReader crawlContextReader) {
		this.crawlContextReader = crawlContextReader;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

}
