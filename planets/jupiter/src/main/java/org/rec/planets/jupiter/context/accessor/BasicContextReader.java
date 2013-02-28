package org.rec.planets.jupiter.context.accessor;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.mercury.expression.ELUtil;

/**
 * 基本的上下文读取器,把传入的key当成一个el表达式处理
 * 
 * @author rec
 * 
 */
public class BasicContextReader implements ContextReader {
	@Override
	public Object read(ActionContext context, String key) {
		return ELUtil.evalFromObject(context, key);
	}
}
