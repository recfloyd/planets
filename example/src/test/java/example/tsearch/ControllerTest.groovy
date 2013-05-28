package example.tsearch

import org.junit.Test
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests

import javax.annotation.Resource

@ContextConfiguration(["classpath:example/tsearch/spring/applicationContext.xml"])
class ControllerTest extends AbstractJUnit4SpringContextTests{
	@Resource(name='controller')
	Controller controller

	@Test
	def void test(){
		controller.run()
	}
}
