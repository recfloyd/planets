package org.rec.planets.jupiter.processor.workflow.iterable;

import org.rec.planets.jupiter.bean.CrawlContext;

/**
 * 循环处理器
 * 通过配置的方式注入循环体
 * @author rec
 *
 */
public class ConfigedIterableProcessor extends AbstractIterableProcessor {
	private Object items;

	public void setItems(Object items) {
		this.items = items;
	}

	@Override
	protected Object getItems(CrawlContext crawlContext) {
		return items;
	}

}
