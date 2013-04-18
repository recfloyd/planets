package org.rec.planets.jupiter.action.assemble.factory;

import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.beans.BeanUtils;

/**
 * 对于含有无参构造函数的类进行初始化
 * 
 * @author rec
 * 
 */
public class BasicBeanFactory implements BeanFactory {
	private Class<?> clazz;

	@Override
	public Object getBean(ActionContext context) {
		return BeanUtils.instantiate(clazz);
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}
