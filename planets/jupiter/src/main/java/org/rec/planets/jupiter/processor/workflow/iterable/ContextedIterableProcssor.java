package org.rec.planets.jupiter.processor.workflow.iterable;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.accessor.CrawlContextReader;

/**
 * 循环处理器 循环体存在于CrawlContext内,通过一个key将其获取
 * 
 * @author rec
 * 
 */
public class ContextedIterableProcssor extends AbstractIterableProcessor {
	private CrawlContextReader crawlContextReader;
	private String itemsKey;

	@Override
	protected Object getItems(CrawlContext crawlContext) {
		return crawlContextReader.get(crawlContext, itemsKey);
	}

	public void setCrawlContextReader(CrawlContextReader crawlContextReader) {
		this.crawlContextReader = crawlContextReader;
	}

	public void setItemsKey(String itemsKey) {
		this.itemsKey = itemsKey;
	}

}
