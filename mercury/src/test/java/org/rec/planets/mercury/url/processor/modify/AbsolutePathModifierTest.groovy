package org.rec.planets.mercury.url.processor.modify;

import static org.junit.Assert.*

import javax.annotation.Resource

import org.junit.Test
import org.rec.planets.mercury.domain.CrawlURL
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests
class AbsolutePathModifierTest extends AbstractModifierTest{
	@Resource(name='absolutePathModifier')
	def AbstractModifier modifier
	@Test
	def void testProcess(){
		def baseURL=new CrawlURL()
		def crawlURL=new CrawlURL()
		
		baseURL.url='http://www.dangdang.com/book/list.html'
		crawlURL.url='a.html'
		modifier.process(crawlURL, baseURL)
		assert crawlURL.url=='http://www.dangdang.com/book/a.html'
		
		baseURL.url='http://www.dangdang.com/book/list.html'
		crawlURL.url='/a.html'
		modifier.process(crawlURL, baseURL)
		assert crawlURL.url=='http://www.dangdang.com/a.html'
		
		baseURL.url='http://www.dangdang.com/book/list.html'
		crawlURL.url='http://www.dangdang.com/a.html'
		modifier.process(crawlURL, baseURL)
		assert crawlURL.url=='http://www.dangdang.com/a.html'
	}
}
