package org.rec.planets.jupiter.action.transfer;

import org.apache.commons.beanutils.Converter;
import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.util.Assert;

/**
 * 封装了一个org.apache.commons.beanutils.Converter用于转换的转换器
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
