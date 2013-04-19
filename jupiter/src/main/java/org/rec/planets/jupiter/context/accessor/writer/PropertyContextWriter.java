package org.rec.planets.jupiter.context.accessor.writer;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 基本的上下文写入器
 * 
 * @author rec
 * 
 */
public class PropertyContextWriter implements ContextWriter {

	@Override
	public void write(Object context, String key, Object result) {
		try {
			PropertyUtils.setNestedProperty(context, key, result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object remove(Object context, String key) {
		try {
			Object value = PropertyUtils.getNestedProperty(context, key);
			PropertyUtils.setNestedProperty(context, key, null);
			return value;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
