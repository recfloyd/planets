package org.rec.planets.mercury.url.processor.validate

import org.junit.Before
import org.junit.Test
import org.rec.planets.mercury.domain.CrawlURL

class NotNullValidatorTest {
	def notNullValidator

	@Before
	def void before(){
		notNullValidator=new NotNullValidator()
		notNullValidator.propertyNames=["url","fingerprint"] as Set<String>
	}

	@Test(expected=Exception)
	def void test(){
		def crawlURL=new CrawlURL()
		crawlURL.id=1
		crawlURL.applicationId=2

		notNullValidator.process(crawlURL,null)
	}
}
