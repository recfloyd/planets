package org.rec.planets.jupiter.context.accessor;

import org.rec.planets.jupiter.action.workflow.iterable.bean.IterableItemStackHolder;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 循环项读取器 用在Iterable循环内 读取的时候从当前线程的ThreadLocal读取当前循环项
 * 
 * @author rec
 * 
 */
public class IterableItemContextReader implements ContextReader {

	@Override
	public Object read(ActionContext context, String key) {
		return IterableItemStackHolder.getItem().getTarget();
	}
}
