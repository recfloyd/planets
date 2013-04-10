package org.rec.planets.mercury.url.processor.property;

import static org.junit.Assert.*

import org.junit.Test
import org.rec.planets.mercury.domain.CrawlURL;

class DirectPropertiesCopierTest {
	def DirectPropertiesCopier directPropertiesCopier;
	@Test
	def void testProcess() {
		CrawlURL baseURL=new CrawlURL();
		CrawlURL crawlURL = new CrawlURL();
		baseURL.applicationId=1
		baseURL.websiteId=1
		baseURL.id=100
		baseURL.fingerprint=123
		baseURL.createTime=new Date()
		
		DirectPropertiesCopier copier=new DirectPropertiesCopier();
		copier.process(crawlURL, baseURL);
		
		System.out.println(crawlURL);
	}

}
