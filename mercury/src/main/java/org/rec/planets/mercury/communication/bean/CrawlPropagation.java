package org.rec.planets.mercury.communication.bean;

import java.util.List;
import java.util.Map;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;

public class CrawlPropagation extends AbstractBean {
	private static final long serialVersionUID = 1429890258172932955L;
	private Long parentId;
	private List<CrawlURL> children;
	private Map<String, Object> content;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<CrawlURL> getChildren() {
		return children;
	}

	public void setChildren(List<CrawlURL> children) {
		this.children = children;
	}

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}
}
