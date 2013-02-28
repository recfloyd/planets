package org.rec.planets.jupiter.processor.interceptor;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.processor.Action;

/**
 * 被拦截的处理器,在一个处理器外包装一个拦截器
 * @author rec
 *
 */
public class InterceptedAction implements Action {
	private Action action;
	private Interceptor interceptor;
	
	public InterceptedAction(Action action, Interceptor interceptor) {
		this.action = action;
		this.interceptor = interceptor;
	}

	@Override
	public void execute(ActionContext context) throws Exception {
		interceptor.invoke(action, context);
	}

}
