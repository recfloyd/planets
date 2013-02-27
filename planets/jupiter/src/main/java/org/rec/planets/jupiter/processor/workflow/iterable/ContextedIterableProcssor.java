package org.rec.planets.jupiter.processor.workflow.iterable;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.accessor.Accessable;
import org.rec.planets.jupiter.processor.accessor.CrawlContextAccessor;

/**
 * 循环处理器
 * 循环体存在于CrawlContext内,通过一个key将其获取
 * @author rec
 *
 */
public class ContextedIterableProcssor extends AbstractIterableProcessor
		implements Accessable {
	private CrawlContextAccessor crawlContextAccessor;
	private String itemsKey;

	public void setItemsKey(String itemsKey) {
		this.itemsKey = itemsKey;
	}

	@Override
	public void setCrawlContextAccessor(
			CrawlContextAccessor crawlContextAccessor) {
		this.crawlContextAccessor = crawlContextAccessor;
	}

	@Override
	protected Object getItems(CrawlContext crawlContext) {
		return crawlContextAccessor.get(crawlContext, itemsKey);
	}

}
