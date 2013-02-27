package org.rec.planets.jupiter.processor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.accessor.CrawlContextReader;
import org.rec.planets.jupiter.processor.accessor.CrawlContextWriter;

/**
 * 抽象的读写处理器 根据CrawlContextAccessor从crawlContext中读取数据,处理后再回写结果
 * 
 * @author rec
 * 
 */
public abstract class AbstractReadWriteProcessor implements CrawlProcessor {
	protected CrawlContextReader crawlContextReader;
	protected CrawlContextWriter crawlContextWriter;
	protected String sourceKey;
	protected String resultKey;
	protected boolean omitSourceNull;
	protected boolean omitResultNull;

	protected abstract Object processInternal(CrawlContext crawlContext,
			Object source) throws Exception;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		Object source = crawlContextReader.get(crawlContext, resultKey);

		if (source == null) {
			if (!omitSourceNull)
				throw new RuntimeException(
						"cannot get source from crawlContext by key "
								+ sourceKey + " of crawlURL "
								+ crawlContext.getCrawlURL());
			return;
		}

		Object result = this.processInternal(crawlContext, source);

		if (result == null) {
			if (!omitResultNull)
				throw new RuntimeException(
						"cannot set result to crawlContext because of null by key "
								+ resultKey + " of crawlURL "
								+ crawlContext.getCrawlURL());
			return;
		}

		crawlContextWriter.set(crawlContext, resultKey, result);
	}

	public void setCrawlContextReader(CrawlContextReader crawlContextReader) {
		this.crawlContextReader = crawlContextReader;
	}

	public void setCrawlContextWriter(CrawlContextWriter crawlContextWriter) {
		this.crawlContextWriter = crawlContextWriter;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public void setOmitSourceNull(boolean omitSourceNull) {
		this.omitSourceNull = omitSourceNull;
	}

	public void setOmitResultNull(boolean omitResultNull) {
		this.omitResultNull = omitResultNull;
	}
}
