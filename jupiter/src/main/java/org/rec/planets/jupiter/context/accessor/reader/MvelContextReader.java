package org.rec.planets.jupiter.context.accessor.reader;

import org.rec.planets.mercury.expression.MvelUtil;

/**
 * 基本的上下文读取器,把传入的key当成一个el表达式处理
 * 
 * @author rec
 * 
 */
public class MvelContextReader implements ContextReader {
	@Override
	public Object read(Object context, String key) {
		return MvelUtil.evalFromObject(context, key);
	}
}
