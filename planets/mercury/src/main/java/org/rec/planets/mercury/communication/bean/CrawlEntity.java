package org.rec.planets.mercury.communication.bean;

import java.io.Serializable;
import java.util.Map;

import org.rec.planets.mercury.domain.AbstractBean;

public class CrawlEntity extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1552755965705097511L;
	/**
	 * 爬行URL
	 */
	private Long parentId;
	/**
	 * 爬行内容
	 */
	private Map<String, ?> content;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Map<String, ?> getContent() {
		return content;
	}

	public void setContent(Map<String, ?> content) {
		this.content = content;
	}
}
