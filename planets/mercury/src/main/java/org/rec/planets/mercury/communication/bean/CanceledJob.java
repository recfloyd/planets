package org.rec.planets.mercury.communication.bean;

import java.io.Serializable;
import java.util.List;

import org.rec.planets.mercury.domain.CrawlURL;

/**
 * 被取消的任务
 * 
 * @author rec
 * 
 */
public class CanceledJob implements Serializable {
	private static final long serialVersionUID = -8989042489816093191L;
	private Long jobId;
	private List<CrawlURL> urls;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public List<CrawlURL> getUrls() {
		return urls;
	}

	public void setUrls(List<CrawlURL> urls) {
		this.urls = urls;
	}
}
