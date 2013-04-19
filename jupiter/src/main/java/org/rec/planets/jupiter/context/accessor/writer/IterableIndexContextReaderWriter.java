package org.rec.planets.jupiter.context.accessor.writer;

import org.rec.planets.jupiter.action.workflow.iterable.bean.IterableItemStackHolder;
import org.rec.planets.jupiter.context.accessor.reader.ContextReader;
import org.rec.planets.mercury.expression.MvelUtil;

/**
 * 基于循环index的读取器,输入的key是一个format字符串,用循环index替换处理之后形成新key,此key认为是一个el表达式
 * 
 * @author rec
 * 
 */
public class IterableIndexContextReaderWriter implements ContextReader,
		ContextWriter {
	private ContextWriter nestedWriter;

	private String getNewKey(String key) {
		return String.format(key, IterableItemStackHolder.getItem().getIndex());
	}

	@Override
	public Object read(Object context, String key) {
		return MvelUtil.evalFromObject(context, getNewKey(key));
	}

	@Override
	public void write(Object context, String key, Object result) {
		nestedWriter.write(context, getNewKey(key), result);
	}

	@Override
	public Object remove(Object context, String key) {
		String newKey = getNewKey(key);
		return nestedWriter.remove(context, newKey);
	}

	public void setNestedWriter(ContextWriter nestedWriter) {
		this.nestedWriter = nestedWriter;
	}

}
