package example.tsearch

import org.junit.Before
import org.junit.Test
import org.rec.planets.jupiter.action.Action
import org.rec.planets.jupiter.context.ActionContext
import org.rec.planets.jupiter.context.ActionContextConstants
import org.rec.planets.jupiter.slot.snapshot.CurrentJobSnapshotFactory
import org.rec.planets.jupiter.slot.snapshot.JobResultSnapshotFactory
import org.rec.planets.jupiter.slot.snapshot.WebsiteSnapshotFactory
import org.rec.planets.mercury.domain.CrawlURL
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests

import javax.annotation.Resource
import java.util.concurrent.ConcurrentHashMap

@ContextConfiguration(["classpath:example/tsearch/rule/rule*.xml"])
class DownloadTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="action")
	Action action
	@Resource(name="websiteProperties")
	Map<String, Object> websiteProperties
	CrawlURL crawlURL
	ActionContext actionContext


	@Before
	def void before(){
		crawlURL=new CrawlURL()
		crawlURL.websiteId=1
		crawlURL.appParams=['keyword':'电脑爱好者']

		actionContext=new ActionContext(crawlURL, websiteProperties, new ConcurrentHashMap<String, Object>())
		actionContext.urlcontext[ActionContextConstants.KEY_JOB_RESULT_SNAPSHORT_FACTORY]=new JobResultSnapshotFactory(1)
		actionContext.urlcontext[ActionContextConstants.KEY_CURRENT_JOB_SNAPSHOT_FACTORY]=new CurrentJobSnapshotFactory(1, 1)
		actionContext.urlcontext[ActionContextConstants.KEY_WEBSITE_SNAPSHOT_FACTORY]=new WebsiteSnapshotFactory(crawlURL.websiteId)
	}

	@Test
	def void test(){
		action.execute(actionContext)
		JobResultSnapshotFactory jsf = actionContext.urlcontext[ActionContextConstants.KEY_JOB_RESULT_SNAPSHORT_FACTORY]
		jsf.entities.each{entity ->
			println entity.content['keyword']
			println '\tfirst_page:'
			entity.content['firstPage'].each{
				println '\t\t'+it
			}
			println '\tlast_page:'
			entity.content['lastPage'].each{
				println '\t\t'+it
			}
		}
		
		jsf.propagations.each{propergation ->
			println 'CrawlPropergation'
			propergation.children.each{url ->
				println url
			}
		}
		
//		println actionContext.urlcontext["first_page_result_trimed"].size()
//		
//		actionContext.urlcontext["first_page_result_trimed"].each{
//			println it
//		}
		
		println actionContext.urlcontext["itemCount"]
		println actionContext.urlcontext["pageCount"]
		
//		println actionContext.urlcontext["last_page_result_trimed"].size()
//
//		actionContext.urlcontext["last_page_result_trimed"].each{ println it }
	}
}
