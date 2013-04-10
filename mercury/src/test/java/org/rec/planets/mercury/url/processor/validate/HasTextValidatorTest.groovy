package org.rec.planets.mercury.url.processor.validate

import org.junit.Before
import org.junit.Test
import org.rec.planets.mercury.domain.CrawlURL

class HasTextValidatorTest {
	def validator

	@Before
	def void before(){
		validator=new HasTextValidator()
		validator.propertyNames=["url"] as Set<String>
	}

	@Test(expected=Exception)
	def void test(){
		def crawlURL=new CrawlURL()
		crawlURL.id=1
		crawlURL.applicationId=2

		validator.process(crawlURL,null)
	}
}
