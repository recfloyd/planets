package org.rec.planets.jupiter.processor.transfer;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.rec.planets.jupiter.processor.accessor.CrawlContextReader;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;

public class CrawlURLTransferer implements CrawlProcessor {
	private CrawlContextReader baseURLReader;
	private CrawlContextReader crawlURLReader;
	private URLProcessor urlProcessor;
	private String crawlURLKey;
	private String baseURLKey;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		CrawlURL crawlURL = (CrawlURL) crawlURLReader.get(crawlContext,
				crawlURLKey);
		CrawlURL baseURL = null;
		if (baseURLReader != null)
			baseURL = (CrawlURL) baseURLReader.get(crawlContext, baseURLKey);
		urlProcessor.process(crawlURL, baseURL);
	}

	public void setBaseURLReader(CrawlContextReader baseURLReader) {
		this.baseURLReader = baseURLReader;
	}

	public void setCrawlURLReader(CrawlContextReader crawlURLReader) {
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
