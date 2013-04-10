package org.rec.planets.jupiter.action.interceptor;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 配合处理器使用的拦截器,可在一个处理器的处理前后添加一些其它操作.类似于Struts的Interceptor
 * @author rec
 *
 */
public interface Interceptor {
	void invoke(Action action,ActionContext context) throws Exception;
}
