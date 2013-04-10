package org.rec.planets.mercury.url.processor.modify;

import javax.annotation.Resource

import org.junit.Test
import org.rec.planets.mercury.domain.CrawlURL

class EncodeModifierTest extends AbstractModifierTest{
	@Resource(name='encodeModifier')
	def AbstractModifier modifier
	@Test
	def void testProcess(){
		def crawlURL=new CrawlURL()
		crawlURL.url='http://www.amazon.cn/诺基亚N1280-简约超长待机直板手机/dp/B003V8AK58/ref=sr_1_1?ie=UTF8&qid=1361246995&sr=8-1'
		modifier.process(crawlURL, null)
		assert crawlURL.url=='http://www.amazon.cn/%E8%AF%BA%E5%9F%BA%E4%BA%9AN1280-%E7%AE%80%E7%BA%A6%E8%B6%85%E9%95%BF%E5%BE%85%E6%9C%BA%E7%9B%B4%E6%9D%BF%E6%89%8B%E6%9C%BA/dp/B003V8AK58/ref=sr_1_1?ie=UTF8&qid=1361246995&sr=8-1'
	}
}
