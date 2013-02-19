package org.rec.planets.mercury.url.processor.modify;

import javax.annotation.Resource

import org.junit.Test
import org.rec.planets.mercury.domain.CrawlURL

class StripRefModifierTest extends AbstractModifierTest{
	@Resource(name='stripRefModifier')
	def AbstractModifier modifier
	@Test
	def void testProcess(){
		def crawlURL=new CrawlURL()
		crawlURL.url='http://www.dangdang.com/book/a.html?x=1&y=2#anchor'
		modifier.process(crawlURL, null)
		assert crawlURL.url=='http://www.dangdang.com/book/a.html?x=1&y=2'
	}
}
