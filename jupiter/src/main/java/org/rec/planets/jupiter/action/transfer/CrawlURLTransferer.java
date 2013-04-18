package org.rec.planets.jupiter.action.transfer;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;

/**
 * url转换器<br/>
 * <code>
 * 	<bean id="转换Action" class="">
 * 		<property name="baseURLReader" ref="基本url读取器"/>
 * 		<property name="baseURLKey" value="基本url读取键"/>
 * 		<property name="crawlURLReader" ref="目标url读取器"/>
 * 		<property name="crawlURLKey" value="目标url读取键"/>
 * 		<property name="urlProcessor" ref="url处理器"/>
 * 	</bean>
 * </code>
 * @author rec
 *
 */
public class CrawlURLTransferer implements Action {
	private ContextReader baseURLReader;
	private ContextReader crawlURLReader;
	private URLProcessor urlProcessor;
	private String crawlURLKey;
	private String baseURLKey;

	@Override
	public void execute(ActionContext context) throws Exception {
		CrawlURL crawlURL = (CrawlURL) crawlURLReader.read(context,
				crawlURLKey);
		CrawlURL baseURL = null;
		if (baseURLReader != null)
			baseURL = (CrawlURL) baseURLReader.read(context, baseURLKey);
		urlProcessor.process(crawlURL, baseURL);
	}

	public void setBaseURLReader(ContextReader baseURLReader) {
		this.baseURLReader = baseURLReader;
	}

	public void setCrawlURLReader(ContextReader crawlURLReader) {
		this.crawlURLReader = crawlURLReader;
	}

	public void setUrlProcessor(URLProcessor urlProcessor) {
		this.urlProcessor = urlProcessor;
	}

	public void setCrawlURLKey(String crawlURLKey) {
		this.crawlURLKey = crawlURLKey;
	}

	public void setBaseURLKey(String baseURLKey) {
		this.baseURLKey = baseURLKey;
	}
}
