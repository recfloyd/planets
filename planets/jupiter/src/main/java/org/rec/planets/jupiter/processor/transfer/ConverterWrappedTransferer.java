package org.rec.planets.jupiter.processor.transfer;

import org.apache.commons.beanutils.Converter;
import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.AbstractReadWriteProcessor;
import org.springframework.util.Assert;

/**
 * 封装了一个org.apache.commons.beanutils.Converter用于转换的转换器
 * @author rec
 *
 */
@SuppressWarnings("rawtypes")
public class ConverterWrappedTransferer extends AbstractReadWriteProcessor {
	private Converter converter;
	private Class clazz;

	@Override
	protected Object processInternal(CrawlContext crawlContext, Object source)
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
