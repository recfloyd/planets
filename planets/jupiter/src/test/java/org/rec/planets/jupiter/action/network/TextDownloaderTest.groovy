package org.rec.planets.jupiter.action.network

import javax.annotation.Resource;

import org.junit.Test;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests
@ContextConfiguration(["classpath:spring/applicationContext-download.xml"])
class TextDownloaderTest extends AbstractJUnit4SpringContextTests {
	@Resource(name="actionContext")
	def actionContext

	@Resource(name="hc4ClientFactory")
	def hc4ClientFactory

	@Resource(name="textDownloader")
	def textDownloader

	@Test
	def void test(){
		actionContext.urlcontext[ActionContextConstants.KEY_CLIENT]=hc4ClientFactory.getClient(actionContext)
		textDownloader.execute(actionContext)
		println actionContext.urlcontext[ActionContextConstants.KEY_HTTP_RESPONSE]
	}
}
