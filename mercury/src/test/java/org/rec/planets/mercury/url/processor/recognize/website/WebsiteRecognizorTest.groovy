package org.rec.planets.mercury.url.processor.recognize.website

import org.junit.Assert;
import org.junit.Before
import org.junit.Test;
import org.rec.planets.mercury.domain.CrawlURL;

class WebsiteRecognizorTest {
	def websiteRecognizor

	@Before
	def void before(){
		websiteRecognizor=new WebsiteRecognizor()
		websiteRecognizor.overwriten=true
		websiteRecognizor.hostRegexes=[(1 as Short):['[a-z0-9\\-._~%]+\\.dangdang\\.com'] as Set<String>,(2 as Short):['^www\\.amazon\\.cn$','^z\\.cn$'] as Set<String>,(3 as Short):['[a-z0-9\\-._~%]+\\.taobao\\.com','[a-z0-9\\-._~%]+\\.tmall\\.com'] as Set<String>]
	}

	@Test
	def void test(){
		def crawlURL=new CrawlURL()

		crawlURL.websiteId=null
		crawlURL.url='http://t.dangdang.com/20121013_0hyp'
		websiteRecognizor.process(crawlURL,null)
		assert crawlURL.websiteId==1

		crawlURL.websiteId=null
		crawlURL.url='http://product.dangdang.com/main2/product.aspx?product_id=410002897&ref=t-8647-3032_5-107015-2'
		websiteRecognizor.process(crawlURL,null)
		assert crawlURL.websiteId==1

		crawlURL.websiteId=null
		crawlURL.url='http://category.dangdang.com/all/?category_id=4008158#ddclick_reco_product_relatecate'
		websiteRecognizor.process(crawlURL,null)
		assert crawlURL.websiteId==1

		crawlURL.websiteId=null
		crawlURL.url='http://www.amazon.cn/gp/product/B008HKEDSA/ref=s9_simh_gw_p351_d0_i3?pf_rd_m=A1AJ19PSB66TGU&pf_rd_s=center-2&pf_rd_r=0RN7FS12XKHAMQB6R6ZD&pf_rd_t=101&pf_rd_p=58223152&pf_rd_i=899254051'
		websiteRecognizor.process(crawlURL,null)
		assert crawlURL.websiteId==2

		crawlURL.websiteId=null
		crawlURL.url='http://www.amazon.cn/gp/bestsellers/digital-text/116169071/ref=pd_zg_hrsr_kinc_1_2'
		websiteRecognizor.process(crawlURL,null)
		assert crawlURL.websiteId==2

		crawlURL.websiteId=null
		crawlURL.url='http://www.amazon.cn/Kindle电子书/b/ref=sv_kinc_1?ie=UTF8&node=116169071'
		websiteRecognizor.process(crawlURL,null)
		assert crawlURL.websiteId==2

		crawlURL.websiteId=null
		crawlURL.url='http://game.taobao.com/item_list.htm?spm=1.1000386.252575.8.nuBHDv&gn=50016986&pidvid=5381751%3A44791&cp=1&quantity=1'
		websiteRecognizor.process(crawlURL,null)
		assert crawlURL.websiteId==3

		crawlURL.websiteId=null
		crawlURL.url='http://list.tmall.com/search_product.htm?spm=3.1000473.294515.34.VTfGeK&active=1&area_code=330100&vmarket=0&wwonline=1&style=g&sort=s&start_price=20&n=60&s=0&cat=50026506'
		websiteRecognizor.process(crawlURL,null)
		assert crawlURL.websiteId==3

		crawlURL.websiteId=null
		crawlURL.url='http://www.coo8.com'
		try{
			websiteRecognizor.process(crawlURL,null)
			Assert.fail()
		}catch(e){
			
		}
	}
}
