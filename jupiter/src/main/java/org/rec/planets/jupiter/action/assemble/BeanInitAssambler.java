package org.rec.planets.jupiter.action.assemble;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.assemble.factory.BeanFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.AbstractReadWriteSupport;

/**
 * Bean初始化组装器<br/>
 * <code>
 * 	<bean id="对象初始化Action" class="">
 * 		<!--以下3个可以省略 如果已经存在则跳过处理-->
 * 		<property name="contextReader" ref="对象读取器"/>
 * 		<property name="sourceKey" value="对象读取键"/>
 * 		<property name="omitSourceNull" value="true"/>
 * 
 * 		<property name="beanFactory" ref="对象工厂"/>
 * 		<property name="contextWriter" ref="对象写入器"/>
 * 		<property name="resultKey" value="对象写入键"/>
 * 		<property name="omitResultNull" value="..."/>
 * 	</bean>
 * </code>
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

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
