package org.rec.planets.mercury.communication.bean.pack;

import java.io.Serializable;
import java.util.Map;

import org.rec.planets.mercury.domain.AbstractBean;

/**
 * slave --> master拉取任务时提供的信息
 * 
 * @author rec
 * 
 */
public class PollPack extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 2227311609594706545L;
	private Short nodeId;
	private Map<Short, Long> ruleVersions;

	public Short getNodeId() {
		return nodeId;
	}

	public void setNodeId(Short nodeId) {
		this.nodeId = nodeId;
	}

	public Map<Short, Long> getRuleVersions() {
		return ruleVersions;
	}

	public void setRuleVersions(Map<Short, Long> ruleVersions) {
		this.ruleVersions = ruleVersions;
	}
}
