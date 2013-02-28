package org.rec.planets.jupiter.processor.interceptor;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.processor.Action;

/**
 * 配合处理器使用的拦截器,可在一个处理器的处理前后添加一些其它操作.类似于Struts的Interceptor
 * @author rec
 *
 */
public interface Interceptor {
	void invoke(Action processor,ActionContext context) throws Exception;
}
