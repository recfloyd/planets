package org.rec.planets.mercury.url.processor.property;

import java.util.Set;

import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class PropertiesReplicater implements URLProcessor {
	private Set<String> propertyNames;

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		BeanWrapper source = new BeanWrapperImpl(baseURL);
		BeanWrapper target = new BeanWrapperImpl(crawlURL);
		for (String propertyName : propertyNames) {
			target.setPropertyValue(propertyName,
					source.getPropertyValue(propertyName));
		}
	}

	public void setPropertyNames(Set<String> propertyNames) {
		this.propertyNames = propertyNames;
	}
}
