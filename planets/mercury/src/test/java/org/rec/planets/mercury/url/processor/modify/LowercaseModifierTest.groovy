package org.rec.planets.mercury.url.processor.modify;

import javax.annotation.Resource

import org.junit.Test
import org.rec.planets.mercury.domain.CrawlURL

class LowercaseModifierTest extends AbstractModifierTest{
	@Resource(name='lowercaseModifier')
	def AbstractModifier modifier
	@Test
	def void testProcess(){
		def crawlURL=new CrawlURL()
		crawlURL.url='HTTP://WWW.DANGDANG.COM/BOOK/A.HTML'
		modifier.process(crawlURL, null)
		assert crawlURL.url=='http://www.dangdang.com/book/a.html'
	}
}
