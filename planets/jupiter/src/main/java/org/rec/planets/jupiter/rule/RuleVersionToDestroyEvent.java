package org.rec.planets.jupiter.rule;

import org.springframework.context.ApplicationEvent;

public class RuleVersionToDestroyEvent extends ApplicationEvent {
	private static final long serialVersionUID = 8616061123470337822L;
	private Short websiteId;
	private Long version;

	public RuleVersionToDestroyEvent(Short websiteId, Long version) {
		super(new Object[] { websiteId, version });
		this.websiteId = websiteId;
		this.version = version;
	}

	public Short getWebsiteId() {
		return websiteId;
	}

	public Long getVersion() {
		return version;
	}

}
