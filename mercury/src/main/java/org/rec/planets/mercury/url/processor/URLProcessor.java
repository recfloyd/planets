package org.rec.planets.mercury.url.processor;

import org.rec.planets.mercury.domain.CrawlURL;

/**
 * URL处理器
 * 
 * @author rec
 * 
 */
public interface URLProcessor {
	void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception;
}
