package org.rec.planets.mercury.url.processor.recognize.fingerprint.combiner;

import org.rec.planets.mercury.domain.CrawlURL;

/**
 * 特征字符串连接器
 * 
 * @author rec
 * 
 */
public interface Combiner {
	String combine(CrawlURL crawlURL, String eigenvalue) throws Exception;
}
