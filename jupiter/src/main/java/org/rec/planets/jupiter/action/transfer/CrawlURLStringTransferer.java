package org.rec.planets.jupiter.action.transfer;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.reader.ContextReader;
import org.rec.planets.jupiter.context.accessor.writer.ContextWriter;
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
 * 		<property name="contextWriter" ref="结果写入器"/>
 * 		<property name="resultKey" value="结果写入键"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class CrawlURLStringTransferer implements Action {
	private ContextReader baseURLReader;
	private ContextReader crawlURLReader;
	private URLProcessor urlProcessor;
	private String crawlURLKey;
	private String baseURLKey;
	private ContextWriter contextWriter;
	private String resultKey;

	@Override
	public void execute(ActionContext context) throws Exception {
		String crawlURLString = (String) crawlURLReader.read(context,
				crawlURLKey);
		CrawlURL crawlURL = new CrawlURL();
		crawlURL.setUrl(crawlURLString);
		CrawlURL baseURL = null;
		if (baseURLReader != null) {
			String baseURLString = (String) baseURLReader.read(context,
					baseURLKey);
			baseURL = new CrawlURL();
			baseURL.setUrl(baseURLString);
		}
		urlProcessor.process(crawlURL, baseURL);
		contextWriter.write(context, resultKey, crawlURL.getUrl());
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

	public void setContextWriter(ContextWriter contextWriter) {
		this.contextWriter = contextWriter;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}
}
