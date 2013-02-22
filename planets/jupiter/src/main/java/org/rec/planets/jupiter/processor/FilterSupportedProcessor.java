package org.rec.planets.jupiter.processor;

import java.util.List;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.filter.Filter;

public class FilterSupportedProcessor implements CrawlProcessor {
	private class Wrapper implements CrawlProcessor {
		private CrawlProcessor processor;
		private Filter filter;
		
		private Wrapper(CrawlProcessor processor, Filter filter) {
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
		// TODO Auto-generated method stub

	}

}
