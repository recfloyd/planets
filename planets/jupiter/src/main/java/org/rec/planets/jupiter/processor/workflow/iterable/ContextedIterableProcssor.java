package org.rec.planets.jupiter.processor.workflow.iterable;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;

/**
 * 循环处理器 循环体存在于CrawlContext内,通过一个key将其获取
 * 
 * @author rec
 * 
 */
public class ContextedIterableProcssor extends AbstractIterableProcessor {
	private ContextReader crawlContextReader;
	private String itemsKey;

	@Override
	protected Object getItems(ActionContext crawlContext) {
		return crawlContextReader.read(crawlContext, itemsKey);
	}

	public void setCrawlContextReader(ContextReader crawlContextReader) {
		this.crawlContextReader = crawlContextReader;
	}

	public void setItemsKey(String itemsKey) {
		this.itemsKey = itemsKey;
	}

}
