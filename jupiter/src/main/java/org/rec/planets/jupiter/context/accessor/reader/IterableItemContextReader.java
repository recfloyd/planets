package org.rec.planets.jupiter.context.accessor.reader;

import org.rec.planets.jupiter.action.workflow.iterable.bean.IterableItemStackHolder;

/**
 * 循环项读取器 用在Iterable循环内 读取的时候从当前线程的ThreadLocal读取当前循环项
 * 
 * @author rec
 * 
 */
public class IterableItemContextReader implements ContextReader {

	@Override
	public Object read(Object context, String key) {
		return IterableItemStackHolder.getItem().getTarget();
	}
}
