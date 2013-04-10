package org.rec.planets.mercury.url.processor.modify;

import javax.annotation.Resource

import org.junit.Test
import org.rec.planets.mercury.domain.CrawlURL

class StripTailModifierTest extends AbstractModifierTest{
	@Resource(name='stripTailModifier')
	def AbstractModifier modifier
	@Test
	def void testProcess(){
		def crawlURL=new CrawlURL()
		crawlURL.url='http://www.amazon.com/Remembering-Whitney-Cissy-Houston/dp/0062238396/ref=sr_1_1?s=books&ie=UTF8&qid=1361247108&sr=1-1'
		modifier.process(crawlURL, null)
		assert crawlURL.url=='http://www.amazon.com/Remembering-Whitney-Cissy-Houston/dp/0062238396'
	}
}
