package org.rec.planets.jupiter.action.workflow.iterable;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;

/**
 * 循环处理器 循环体存在于ActionContext内,通过一个key将其获取<br/>
 * <code>
 * 	<bean id="循环Action" class="">
 * 		<property name="nestedAction" ref="..."/>
 * 		<property name="contextReader" ref="循环体读取器"/>
 * 		<property name="itemsKey" value="循环体读取键"/>
 * 		<property name="omitAbsence" value="是否忽略空循环体"/>
 * 		<property name="parallel" value="是否并行"/>
 * 		<property name="threadPoolFactory" ref="并行线程池"/>
 * 		<property name="omitException" value="是否忽略异常"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class ContextedIterableAction extends AbstractIterableAction {
	private ContextReader contextReader;
	private String itemsKey;

	@Override
	protected Object getItems(ActionContext context) {
		return contextReader.read(context, itemsKey);
	}

	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

	public void setItemsKey(String itemsKey) {
		this.itemsKey = itemsKey;
	}
}
