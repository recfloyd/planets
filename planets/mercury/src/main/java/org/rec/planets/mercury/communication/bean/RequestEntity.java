package org.rec.planets.mercury.communication.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.rec.planets.mercury.domain.AbstractBean;

public class RequestEntity  extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 节点
	 */
	private Short nodeId;
	/**
	 * 规则版本
	 */
	private Map<Short, Integer> ruleVersions;
	/**
	 * 网站统计
	 */
	private List<CrawlWebsiteStat> websiteStats;

	public Short getNodeId() {
		return nodeId;
	}

	public void setNodeId(Short nodeId) {
		this.nodeId = nodeId;
	}

	public Map<Short, Integer> getRuleVersions() {
		return ruleVersions;
	}

	public void setRuleVersions(Map<Short, Integer> ruleVersions) {
		this.ruleVersions = ruleVersions;
	}

	public List<CrawlWebsiteStat> getWebsiteStats() {
		return websiteStats;
	}

	public void setWebsiteStats(List<CrawlWebsiteStat> websiteStats) {
		this.websiteStats = websiteStats;
	}

}
