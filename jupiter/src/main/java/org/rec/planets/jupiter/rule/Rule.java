package org.rec.planets.jupiter.rule;

import java.util.Map;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.mercury.domain.AbstractBean;

/**
 * 抓取规则
 * 
 * @author rec
 * 
 */
public class Rule extends AbstractBean {
	private static final long serialVersionUID = 6729153572329461890L;
	private Short websiteId;
	private Long version;
	private Map<String, Object> websiteProperties;
	private Action action;

	public Rule(Short websiteId, Long version,
			Map<String, Object> websiteProperties, Action action) {
		this.websiteId = websiteId;
		this.version = version;
		this.websiteProperties = websiteProperties;
		this.action = action;
	}

	public Short getWebsiteId() {
		return websiteId;
	}

	public Long getVersion() {
		return version;
	}

	public Map<String, Object> getWebsiteProperties() {
		return websiteProperties;
	}

	public Action getAction() {
		return action;
	}

}
