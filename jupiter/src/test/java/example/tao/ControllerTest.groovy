package example.tao;

import java.util.concurrent.ConcurrentHashMap

import javax.annotation.Resource

import org.junit.Before
import org.junit.Test
import org.rec.planets.jupiter.context.ActionContext
import org.rec.planets.jupiter.context.ActionContextConstants
import org.rec.planets.jupiter.slot.snapshot.CurrentJobSnapshotFactory
import org.rec.planets.jupiter.slot.snapshot.JobResultSnapshotFactory
import org.rec.planets.jupiter.slot.snapshot.WebsiteSnapshotFactory
import org.rec.planets.mercury.domain.CrawlURL
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests
@ContextConfiguration(["classpath:example/tao/spring/applicationContext.xml"])
class ControllerTest extends AbstractJUnit4SpringContextTests{
	@Resource(name='controller')
	Controller controller

	@Test
	def void test(){
		controller.run()
	}
}
