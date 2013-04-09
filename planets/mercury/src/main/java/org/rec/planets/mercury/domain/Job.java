package org.rec.planets.mercury.domain;

import java.util.List;

import org.rec.planets.mercury.domain.constant.JobMode;

/**
 * 任务
 * 
 * @author lijia
 * 
 */
public class Job extends BusinessEntity{
	private static final long serialVersionUID = 9170610141753990180L;
	private Long id;
	/**
	 * 网站
	 */
	private Short websiteId;
	/**
	 * 节点
	 */
	private Short nodeId;
	/**
	 * url
	 */
	private List<CrawlURL> urls;
	/**
	 * 任务模式,主动/被动
	 */
	private JobMode jobMode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Short websiteId) {
		this.websiteId = websiteId;
	}

	public Short getNodeId() {
		return nodeId;
	}

	public void setNodeId(Short nodeId) {
		this.nodeId = nodeId;
	}

	public List<CrawlURL> getUrls() {
		return urls;
	}

	public void setUrls(List<CrawlURL> urls) {
		this.urls = urls;
	}

	public JobMode getJobMode() {
		return jobMode;
	}

	public void setJobMode(JobMode jobMode) {
		this.jobMode = jobMode;
	}
}
