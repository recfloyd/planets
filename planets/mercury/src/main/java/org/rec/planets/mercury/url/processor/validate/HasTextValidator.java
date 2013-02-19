package org.rec.planets.mercury.url.processor.validate;

import java.util.Set;

import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.google.common.base.Strings;

public class HasTextValidator implements URLProcessor {
	private Set<String> propertyNames;

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		BeanWrapper bw = new BeanWrapperImpl(crawlURL);
		@SuppressWarnings("rawtypes")
		Class clazz = null;
		String value = null;
		for (String propertyName : propertyNames) {
			clazz = bw.getPropertyType(propertyName);
			if (!clazz.equals(String.class))
				throw new IllegalArgumentException("hash text property["
						+ propertyName + "] is not String, crawlURL: "
						+ crawlURL);
			value = (String) bw.getPropertyValue(propertyName);
			if (Strings.isNullOrEmpty(value))
				throw new Exception("hash text property[" + propertyName
						+ "] is empty, crawlURL: " + crawlURL);
		}
	}

	public void setPropertyNames(Set<String> propertyNames) {
		this.propertyNames = propertyNames;
	}
}
