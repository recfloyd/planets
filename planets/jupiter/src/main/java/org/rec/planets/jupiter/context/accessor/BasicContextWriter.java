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
public class BasicContextWriter implements ContextWriter {

	@Override
	public void write(ActionContext context, String key, Object result) {
		if (result != null) {
			BeanWrapper bw = new BeanWrapperImpl(context);
			bw.setPropertyValue(key, result);
		}

	}
}
