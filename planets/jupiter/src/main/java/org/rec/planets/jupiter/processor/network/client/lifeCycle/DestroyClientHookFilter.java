package org.rec.planets.jupiter.processor.network.client.lifeCycle;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessable;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessor;
import org.rec.planets.jupiter.processor.filter.Filter;
import org.rec.planets.jupiter.processor.network.client.Client;

/**
 * http客户端销毁过滤器,执行完下载之后,此过滤器将客户端销毁
 * 
 * @author rec
 * 
 */
public class DestroyClientHookFilter implements Filter, CrawlContextAccessable {
	protected CrawlContextAccessor crawlContextAccessor;

	@Override
	public void invoke(CrawlProcessor processor, CrawlContext crawlContext)
			throws Exception {
		try {
			processor.process(crawlContext);
		} finally {
			Client client = (Client) crawlContextAccessor.get(crawlContext);
			if (client != null)
				client.destroy();
		}
	}

	@Override
	public void setCrawlContextAccessor(
			CrawlContextAccessor crawlContextAccessor) {
		this.crawlContextAccessor = crawlContextAccessor;
	}
}
