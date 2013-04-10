package org.rec.planets.mercury.url.processor.validate;

import java.util.Set;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 确认属性非空
 * 
 * @author rec
 * 
 */
public class NotNullValidator extends AbstractBean implements URLProcessor {
	private static final long serialVersionUID = 6838682617243404489L;
	private Set<String> propertyNames;

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		BeanWrapper bw = new BeanWrapperImpl(crawlURL);
		Object value = null;
		for (String propertyName : propertyNames) {
			value = bw.getPropertyValue(propertyName);
			if (value == null)
				throw new Exception("not null property[" + propertyName
						+ "] is found to be null, crawlURL: " + crawlURL);
		}
	}

	public void setPropertyNames(Set<String> propertyNames) {
		this.propertyNames = propertyNames;
	}
}
