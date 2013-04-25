package example.dangdang

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

@ContextConfiguration(["classpath:example/dangdang/rule/rule*.xml"])
class DownloadTest extends AbstractJUnit4SpringContextTests{
	@Resource(name="action")
	Action action
	@Resource(name="websiteProperties")
	Map<String, Object> websiteProperties

    def printJobResult={actionContext ->
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

	@Test
	def void testMain(){
        CrawlURL crawlURL=new CrawlURL()
        crawlURL.websiteId=3
        crawlURL.pageType=10
        crawlURL.url='http://www.dangdang.com'

        ActionContext actionContext=new ActionContext(crawlURL, websiteProperties, new ConcurrentHashMap<String, Object>())
        actionContext.urlcontext[ActionContextConstants.KEY_JOB_RESULT_SNAPSHORT_FACTORY]=new JobResultSnapshotFactory(1)
        actionContext.urlcontext[ActionContextConstants.KEY_CURRENT_JOB_SNAPSHOT_FACTORY]=new CurrentJobSnapshotFactory(1, 1)
        actionContext.urlcontext[ActionContextConstants.KEY_WEBSITE_SNAPSHOT_FACTORY]=new WebsiteSnapshotFactory(crawlURL.websiteId)

		action.execute(actionContext)

        printJobResult(actionContext)
	}

    @Test
    def void testSitemap(){
        CrawlURL crawlURL=new CrawlURL()
        crawlURL.websiteId=3
        crawlURL.pageType=15
        crawlURL.url='http://category.dangdang.com'

        ActionContext actionContext=new ActionContext(crawlURL, websiteProperties, new ConcurrentHashMap<String, Object>())
        actionContext.urlcontext[ActionContextConstants.KEY_JOB_RESULT_SNAPSHORT_FACTORY]=new JobResultSnapshotFactory(1)
        actionContext.urlcontext[ActionContextConstants.KEY_CURRENT_JOB_SNAPSHOT_FACTORY]=new CurrentJobSnapshotFactory(1, 1)
        actionContext.urlcontext[ActionContextConstants.KEY_WEBSITE_SNAPSHOT_FACTORY]=new WebsiteSnapshotFactory(crawlURL.websiteId)

        action.execute(actionContext)

        printJobResult(actionContext)
    }
}
