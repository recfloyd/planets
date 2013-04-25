package example.tao

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

@ContextConfiguration(["classpath:example/tao/rule/rule*.xml"])
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
		crawlURL.url='http://list.tmall.com/search_product.htm?spm=a220m.1000858.0.493.v34gUE&active=1&suggest=0_1&from=sn_1_cat-qp&area_code=440000&style=g&sort=d&q=%BD%F8%BF%DA&n=60&s=0&cat=50072513'
		crawlURL.appParams=['todoPage':10,'type':'饼干蛋糕']

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
			println 'CrawlEntity'
			def list=entity.content['product_list']
			list.each{product ->
				product.each{ k,v ->
					println "$k -> $v"
				}
				println '-------------------------------'
			}
		}
		
		jsf.propagations.each{propergation ->
			println 'CrawlPropergation'
			propergation.children.each{url ->
				println url
			}
		}
	}
}
