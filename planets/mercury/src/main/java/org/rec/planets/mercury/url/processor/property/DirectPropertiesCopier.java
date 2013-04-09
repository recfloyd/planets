package org.rec.planets.mercury.url.processor.property;

import java.beans.PropertyDescriptor;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.springframework.util.CollectionUtils;

public class DirectPropertiesCopier extends AbstractBean implements
		URLProcessor {
	private static final long serialVersionUID = -7912220396329796590L;
	/**
	 * 如果为空,表示全部copy
	 */
	private Set<String> propertyNames;

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		boolean allAllowed = CollectionUtils.isEmpty(propertyNames);
		PropertyDescriptor[] propertyDescriptors = PropertyUtils
				.getPropertyDescriptors(CrawlURL.class);
		String propertyName = null;
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			propertyName = propertyDescriptor.getName();
			if ((allAllowed || propertyNames.contains(propertyName))
					&& PropertyUtils.isWriteable(crawlURL, propertyName)) {
				PropertyUtils.setProperty(crawlURL, propertyName,
						PropertyUtils.getProperty(baseURL, propertyName));
			}
		}
	}

	public void setPropertyNames(Set<String> propertyNames) {
		this.propertyNames = propertyNames;
	}
}
