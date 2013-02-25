package org.rec.planets.jupiter.processor;

import org.rec.planets.jupiter.bean.CrawlContext;

/**
 * 被过滤的处理器,在一个处理器外包装一个过滤器
 * @author rec
 *
 */
public class FilteredProcessor implements CrawlProcessor {
	private CrawlProcessor processor;
	private Filter filter;
	
	
	public FilteredProcessor(CrawlProcessor processor, Filter filter) {
		this.processor = processor;
		this.filter = filter;
	}
	
	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		filter.invoke(processor, crawlContext);
	}

}
