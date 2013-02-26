package org.rec.planets.jupiter.processor.parse;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.Accessable;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessor;

/**
 * 抽象解析器,它含有一个源key,一个结果key.并含有2个开关控制量,指示当无法获取源或者无法生成结果时是否抛出异常
 * 
 * @author rec
 * 
 */
public abstract class AbstractParser implements CrawlProcessor,Accessable {
	protected String sourceKey;
	protected String resultKey;
	protected boolean omitSourceAbsence;
	protected boolean omitResultAbsence;
	protected CrawlContextAccessor crawlContextAccessor;

	protected abstract Object parseInternal(CrawlContext crawlContext,
			Object source) throws Exception;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		Object source = crawlContextAccessor.get(crawlContext, sourceKey);
		if (source == null) {
			if (omitSourceAbsence)
				return;
			else
				throw new IllegalArgumentException(
						"cannot get source from context by key " + sourceKey
								+ " of CrawlURL " + crawlContext.getCrawlURL());
		}
		Object result = this.parseInternal(crawlContext, source);
		if (result == null) {
			if (omitResultAbsence)
				return;
			else
				throw new RuntimeException(
						"cannot parse result to context by key " + resultKey
								+ " of CrawlURL " + crawlContext.getCrawlURL());
		}
		crawlContextAccessor.set(crawlContext, resultKey, result);
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public void setOmitSourceAbsence(boolean omitSourceAbsence) {
		this.omitSourceAbsence = omitSourceAbsence;
	}

	public void setOmitResultAbsence(boolean omitResultAbsence) {
		this.omitResultAbsence = omitResultAbsence;
	}
	@Override
	public void setCrawlContextAccessor(CrawlContextAccessor crawlContextAccessor) {
		this.crawlContextAccessor = crawlContextAccessor;
	}
}
