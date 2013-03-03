package org.rec.planets.mercury.communication.bean;

import java.util.List;

import org.rec.planets.mercury.domain.CrawlURL;

/**
 * 被取消的任务
 * 
 * @author rec
 * 
 */
public interface CanceledJob {
	Long getJobId();

	List<CrawlURL> getCrawlURLs();
}
