package org.rec.planets.jupiter.processor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessable;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessor;
import org.springframework.util.Assert;

/**
 * 抽象的读写处理器 根据CrawlContextAccessor从crawlContext中读取数据,处理后再回写结果
 * 
 * @author rec
 * 
 */
public abstract class AbstractReadWriteProcessor implements CrawlProcessor,
		CrawlContextAccessable {
	protected CrawlContextAccessor crawlContextAccessor;

	protected abstract Object processInternal(CrawlContext crawlContext,
			Object source) throws Exception;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		Assert.notNull(crawlContextAccessor,
				"crawlContextAccessor is null and cannot read or write");

		Object source = crawlContextAccessor.get(crawlContext);

		if (source == null)
			return;

		Object result = this.processInternal(crawlContext, source);

		if (result == null)
			return;

		crawlContextAccessor.set(crawlContext, result);
	}

	@Override
	public void setCrawlContextAccessor(
			CrawlContextAccessor crawlContextAccessor) {
		this.crawlContextAccessor = crawlContextAccessor;
	}
}
