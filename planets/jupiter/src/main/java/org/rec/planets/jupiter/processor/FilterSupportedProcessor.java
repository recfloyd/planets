package org.rec.planets.jupiter.processor;

import java.util.List;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.filter.Filter;

public class FilterSupportedProcessor implements CrawlProcessor {
	private class FilterWrappedProcessor implements CrawlProcessor {
		private CrawlProcessor processor;
		private Filter filter;

		private FilterWrappedProcessor(CrawlProcessor processor, Filter filter) {
			this.processor = processor;
			this.filter = filter;
		}

		@Override
		public void process(CrawlContext crawlContext) throws Exception {
			filter.invoke(processor, crawlContext);
		}

	}

	private CrawlProcessor targetProcessor;

	private List<Filter> filters;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		CrawlProcessor wrapped = targetProcessor;
		for (int i = filters.size() - 1; i > -1; i--) {
			wrapped = new FilterWrappedProcessor(wrapped, filters.get(i));
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
