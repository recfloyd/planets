package org.rec.planets.mercury.url.processor.recognize.page

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests

@ContextConfiguration(["classpath:spring/applicationContext-recognize.xml"])
class PageTypeRecognizorTest extends AbstractJUnit4SpringContextTests {
	@Resource(name="pageTypeRecognizor")
	def pageTypeRecognizor
	
	@Test
	def void test(){
		println pageTypeRecognizor
	}
}
