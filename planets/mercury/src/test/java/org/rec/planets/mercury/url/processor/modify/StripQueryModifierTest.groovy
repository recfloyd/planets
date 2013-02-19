package org.rec.planets.mercury.url.processor.modify;

import javax.annotation.Resource

import org.junit.Test
import org.rec.planets.mercury.domain.CrawlURL

class StripQueryModifierTest extends AbstractModifierTest{
	@Resource(name='stripQueryModifier')
	def AbstractModifier modifier
	@Test
	def void testProcess(){
		def crawlURL=new CrawlURL()
		crawlURL.url='http://www.dangdang.com/book/a.html?x=1&y=2'
		modifier.process(crawlURL, null)
		assert crawlURL.url=='http://www.dangdang.com/book/a.html'
	}
}
