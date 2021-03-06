package org.rec.planets.jupiter.action.interceptor;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 被拦截的处理器,在一个处理器外包装一个拦截器<br/>
 * <code>
 * 	<bean id="增加了拦截功能的Action" class="">
 * 		<constructor-arg name="action" ref="实际工作的action"/>
 * 		<constructor-arg name="interceptor" ref="拦截器"/>
 * 	</bean>
 * </code>
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
