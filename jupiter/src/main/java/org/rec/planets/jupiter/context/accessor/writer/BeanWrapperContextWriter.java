package org.rec.planets.jupiter.context.accessor.writer;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanWrapperContextWriter implements ContextWriter {

	@Override
	public void write(Object context, String key, Object result) {
		BeanWrapper bw = new BeanWrapperImpl(context);
		bw.setPropertyValue(key, result);
	}

	@Override
	public Object remove(Object context, String key) {
		BeanWrapper bw = new BeanWrapperImpl(context);
		Object value = bw.getPropertyValue(key);
		bw.setPropertyValue(key, null);
		return value;
	}

}
