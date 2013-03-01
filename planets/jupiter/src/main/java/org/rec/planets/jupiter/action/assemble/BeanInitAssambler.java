package org.rec.planets.jupiter.action.assemble;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.assemble.factory.BeanFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;
import org.rec.planets.jupiter.context.accessor.ContextWriter;

/**
 * Bean初始化组装器
 * @author rec
 *
 */
public class BeanInitAssambler implements Action {
	private ContextReader contextReader;
	private ContextWriter contextWriter;
	private String key;
	private BeanFactory beanFactory;

	@Override
	public void execute(ActionContext context) throws Exception {
		if (contextReader != null) {// 首先检查一下是否为null
			Object obj = contextReader.read(context, key);
			if (obj != null)
				return;
		}
		Object obj = beanFactory.getBean(context);
		contextWriter.write(context, key, obj);
	}

	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

	public void setContextWriter(ContextWriter contextWriter) {
		this.contextWriter = contextWriter;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
