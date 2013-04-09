package org.rec.planets.jupiter.action.interceptor;

import java.util.List;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 包装了一组拦截器和一个核心操作的处理器.类似于Struts中将一组Interceptor包装到一个Action上<br/>
 * <code>
 * 	<bean id="增加了组拦截功能的Action" class="">
 * 		<property name="action" ref="实际工作的action"/>
 * 		<property name="interceptors">
 * 			<list>
 * 				...
 * 			</list>
 * 		</property>
 * 	</bean>
 * </code>
 * @author rec
 * 
 */
public class InterceptorChainSupportedAction implements Action {
	private Action action;

	private List<Interceptor> interceptors;

	@Override
	public void execute(ActionContext context) throws Exception {
		Action wrapped = action;
		Interceptor interceptor = null;
		for (int i = interceptors.size() - 1; i > -1; i--) {
			interceptor = interceptors.get(i);
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
