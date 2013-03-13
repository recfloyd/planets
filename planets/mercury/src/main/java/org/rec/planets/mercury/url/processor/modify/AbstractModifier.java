package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.url.processor.URLProcessor;

public abstract class AbstractModifier extends AbstractBean implements URLProcessor {

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		crawlURL.setUrl(this.modify(crawlURL.getUrl(), baseURL == null ? null
				: baseURL.getUrl()));
	}

	protected abstract String modify(String url, String baseURL);
}
