package org.rec.planets.jupiter.context.accessor;

import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 基本的上下文写入器
 * 
 * @author rec
 * 
 */
public class PropertyContextWriter implements ContextWriter {

	@Override
	public void write(ActionContext context, String key, Object result) {
		BeanWrapper bw = new BeanWrapperImpl(context);
		bw.setPropertyValue(key, result);
	}

	@Override
	public Object remove(ActionContext context, String key) {
		BeanWrapper bw = new BeanWrapperImpl(context);
		Object obj = bw.getPropertyValue(key);
		bw.setPropertyValue(key, null);
		return obj;
	}
}
