package org.rec.planets.mercury.communication.bean;

import java.io.Serializable;
import java.util.List;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;

public class CrawlPropagation extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1429890258172932955L;
	private Long parentId;
	private List<CrawlURL> children;

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
}
