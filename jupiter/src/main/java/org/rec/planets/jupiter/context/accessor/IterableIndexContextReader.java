package org.rec.planets.jupiter.context.accessor;

import org.rec.planets.jupiter.action.workflow.iterable.bean.IterableItemStackHolder;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.mercury.expression.JexlUtil;

/**
 * 基于循环index的读取器,输入的key是一个format字符串,用循环index替换处理之后形成新key,此key认为是一个el表达式
 * 
 * @author rec
 * 
 */
public class IterableIndexContextReader implements ContextReader {

	@Override
	public Object read(ActionContext context, String key) {
		String newKey = String.format(key, IterableItemStackHolder.getItem()
				.getIndex());
		return JexlUtil.evalFromObject(context, newKey);
	}

}
