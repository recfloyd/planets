package org.rec.planets.jupiter.action.assemble.factory;

import org.rec.planets.jupiter.context.ActionContext;

public interface BeanFactory {
	Object getBean(ActionContext context);
}
