package org.rec.planets.jupiter.action.assemble;

import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 属性设置组装器,它从某个地方读取一个值,不做任何处理直接复制到另外一个地方<br/>
 * <code>
 * 	<bean id="值转储Action" class="">
 * 		<property name="contextReader" ref="源读取器"/>
 * 		<property name="sourceKey" value="源读取键"/>
 * 		<property name="omitSourceNull" value="..."/>
 * 
 * 		<property name="contextWriter" ref="目标写入器"/>
 * 		<property name="resultKey" value="目标写入键"/>
 * 		<property name="omitResultNull" value="..."/>
 * 	</bean>
 * </code>
 * @author rec
 * 
 */
public class ObjectCopyAssambler extends AbstractReadProcessWriteAction {

	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		return source;
	}

}
