package org.rec.planets.jupiter.action.transfer;

import org.apache.commons.beanutils.Converter;
import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.util.Assert;

/**
 * 封装了一个org.apache.commons.beanutils.Converter用于转换的转换器<br/>
 * <code>
 * 	<bean id="转换Action" class="">
 * 		<property name="contextReader" ref="源读取器"/>
 * 		<property name="sourceKey" ref="源读取键"/>
 * 		<property name="omitSourceNull" ref="false"/>
 * 		<property name="contextWriter" ref="目标写入器"/>
 * 		<property name="resultKey" ref="目标写入键"/>
 * 		<property name="omitSourceNull" ref="false"/>
 * 		<property name="converter" ref="转换器"/>
 * 		<property name="clazz" ref="..."/>
 * 	</bean>
 * </code>
 * @author rec
 *
 */
@SuppressWarnings("rawtypes")
public class ConverterWrappedTransferer extends AbstractReadProcessWriteAction {
	private Converter converter;
	private Class clazz;

	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		Assert.notNull(converter, "converter is null");
		return converter.convert(clazz, source);
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
}
