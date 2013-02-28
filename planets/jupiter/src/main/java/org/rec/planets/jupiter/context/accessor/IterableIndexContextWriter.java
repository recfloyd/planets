package org.rec.planets.jupiter.context.accessor;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.processor.workflow.iterable.bean.IterableItemStackHolder;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 基于循环index的读取器,输入的key是一个format字符串,用循环index替换处理之后形成新key,此key认为是一个属性表达式
 * 
 * @author rec
 * 
 */
public class IterableIndexContextWriter implements ContextWriter {
	@Override
	public void write(ActionContext context, String key, Object result) {
		String newKey = String.format(key, IterableItemStackHolder.getItem()
				.getIndex());
		BeanWrapper bw = new BeanWrapperImpl(context);
		bw.setPropertyValue(newKey, result);
	}
}
