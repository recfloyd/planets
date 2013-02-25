package org.rec.planets.jupiter.processor;

import org.rec.planets.jupiter.bean.CrawlContext;

/**
 * 配合处理器使用的过滤器,可在一个处理器的处理前后添加一些其它操作.类似于Servlet的Filter
 * @author rec
 *
 */
public interface Filter {
	void invoke(CrawlProcessor processor,CrawlContext crawlContext) throws Exception;
}
