package org.rec.planets.jupiter.processor.workflow.conditioned;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.CrawlContextReader;

/**
 * 基于条件判断的处理器
 * 
 * @author rec
 * 
 */
public abstract class AbstractConditionedProcessor implements CrawlProcessor {
	private CrawlContextReader crawlContextReader;
	private String evalString;

	protected boolean eval(CrawlContext crawlContext) {
		Boolean result = (Boolean) crawlContextReader.get(crawlContext,
				evalString);
		return result == null ? false : result;
	}

	public void setCrawlContextReader(CrawlContextReader crawlContextReader) {
		this.crawlContextReader = crawlContextReader;
	}

	public void setEvalString(String evalString) {
		this.evalString = evalString;
	}

}
