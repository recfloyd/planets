package org.rec.planets.mercury.communication.bean;

import java.util.Map;

import org.rec.planets.mercury.domain.AbstractBean;

public class CrawlEntity extends AbstractBean {
	private static final long serialVersionUID = 1552755965705097511L;
	/**
	 * 爬行URL
	 */
	private Long parentId;
	/**
	 * 爬行内容
	 */
	private Map<String, Object> content;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}
}
