package org.rec.planets.mercury.domain;

import java.io.Serializable;

import org.rec.planets.mercury.domain.constant.CrawlType;

public class Application extends BusinessEntity implements Serializable {
	private static final long serialVersionUID = 2194251834364836811L;
	private Short id;
	/**
	 * 应用名
	 */
	private String name;
	/**
	 * 应用描述
	 */
	private String description;
	/**
	 * 爬虫类型
	 */
	private CrawlType crawlType;

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CrawlType getCrawlType() {
		return crawlType;
	}

	public void setCrawlType(CrawlType crawlType) {
		this.crawlType = crawlType;
	}
}
