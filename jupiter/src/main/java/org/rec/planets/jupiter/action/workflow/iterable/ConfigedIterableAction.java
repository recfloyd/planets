package org.rec.planets.jupiter.action.workflow.iterable;

import org.rec.planets.jupiter.context.ActionContext;

/**
 * 循环处理器
 * 通过配置的方式注入循环体<br/>
 * <code>
 * 	<bean id="循环Action" class="">
 * 		<property name="nestedAction" ref="..."/>
 * 		<property name="items" ref="循环体,必须是可迭代的"/>
 * 		<property name="omitAbsence" ref="是否忽略空循环体"/>
 * 		<property name="parallel" ref="是否并行"/>
 * 		<property name="threadPoolFactory" ref="并行线程池"/>
 * 		<property name="omitException" ref="是否忽略异常"/>
 * 	</bean>
 * </code>
 * @author rec
 *
 */
public class ConfigedIterableAction extends AbstractIterableAction {
	private Object items;

	public void setItems(Object items) {
		this.items = items;
	}

	@Override
	protected Object getItems(ActionContext context) {
		return items;
	}

}
