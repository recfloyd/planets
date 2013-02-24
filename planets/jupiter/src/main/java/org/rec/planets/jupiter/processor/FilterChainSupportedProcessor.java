package org.rec.planets.jupiter.processor;

import java.util.List;

import org.rec.planets.jupiter.bean.CrawlContext;

public class FilterChainSupportedProcessor implements CrawlProcessor {
	private CrawlProcessor targetProcessor;

	private List<Filter> filters;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		CrawlProcessor wrapped = targetProcessor;
		for (Filter filter : filters) {
			wrapped = new FilteredProcessor(wrapped, filter);
		}
		wrapped.process(crawlContext);
	}

	public void setTargetProcessor(CrawlProcessor targetProcessor) {
		this.targetProcessor = targetProcessor;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
}
