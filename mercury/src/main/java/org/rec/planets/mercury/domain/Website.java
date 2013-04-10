package org.rec.planets.mercury.domain;

import java.util.List;

import org.rec.planets.mercury.domain.constant.CrawlType;

/**
 * 网站
 * 
 * @author rec
 * 
 */
public class Website extends BusinessEntity{
	private static final long serialVersionUID = -186361245479562089L;
	private Short id;
	/**
	 * 网站名
	 */
	private String name;
	/**
	 * 别名
	 */
	private String alias;
	/**
	 * 网站域名
	 */
	private List<String> domains;
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

	public List<String> getDomains() {
		return domains;
	}

	public void setDomains(List<String> domains) {
		this.domains = domains;
	}

	public CrawlType getCrawlType() {
		return crawlType;
	}

	public void setCrawlType(CrawlType crawlType) {
		this.crawlType = crawlType;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
