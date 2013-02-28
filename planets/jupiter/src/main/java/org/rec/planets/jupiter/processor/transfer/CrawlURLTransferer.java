package org.rec.planets.jupiter.processor.transfer;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;
import org.rec.planets.jupiter.processor.Action;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;

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
