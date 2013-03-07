package org.rec.planets.jupiter.action.assemble;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.assemble.factory.BeanFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.AbstractReadWriteSupport;

/**
 * Bean初始化组装器
 * 
 * @author rec
 * 
 */
public class BeanInitAssambler extends AbstractReadWriteSupport implements
		Action {
	private BeanFactory beanFactory;

	@Override
	public void execute(ActionContext context) throws Exception {
		if (contextReader != null) {// 首先检查一下是否为null
			Object obj = getSource(context);
			if (obj != null)
				return;
		}
		Object obj = beanFactory.getBean(context);
		writeResult(context, obj);
	}
}
