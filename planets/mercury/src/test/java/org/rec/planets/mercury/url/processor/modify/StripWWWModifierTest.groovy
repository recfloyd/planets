package org.rec.planets.mercury.url.processor.modify;

import javax.annotation.Resource

import org.junit.Test
import org.rec.planets.mercury.domain.CrawlURL

class StripWWWModifierTest extends AbstractModifierTest{
	@Resource(name='stripWWWModifier')
	def AbstractModifier modifier
	@Test
	def void testProcess(){
		def crawlURL=new CrawlURL()
		crawlURL.url='http://www.dangdang.com/book/a.html'
		modifier.process(crawlURL, null)
		assert crawlURL.url=='http://dangdang.com/book/a.html'
	}
}
