package org.rec.planets.jupiter.processor.interceptor;

import java.util.List;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.processor.Action;

/**
 * 包装了一组拦截器和一个核心操作的处理器.类似于Struts中将一组Interceptor包装到一个Action上
 * @author rec
 *
 */
public class InterceptorChainSupportedAction implements Action {
	private Action action;

	private List<Interceptor> interceptors;

	@Override
	public void execute(ActionContext context) throws Exception {
		Action wrapped = action;
		for (Interceptor interceptor : interceptors) {
			wrapped = new InterceptedAction(wrapped, interceptor);
		}
		wrapped.execute(context);
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setInterceptors(List<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}
}
