package org.rec.planets.mercury.domain;

import java.util.Map;

import org.rec.planets.mercury.domain.constant.CrawlType;

/**
 * 待抓URL
 * 
 * @author rec
 * 
 */
public class CrawlURL extends BusinessEntity{
	private static final long serialVersionUID = 3822094611022925273L;
	private Long id;
	/**
	 * 指纹
	 */
	private Long fingerprint;
	/**
	 * 网站id
	 */
	private Short websiteId;
	/**
	 * 应用id
	 */
	private Short applicationId;
	/**
	 * 页面类型
	 */
	private Byte pageType;
	/**
	 * url
	 */
	private String url;
	/**
	 * 父id
	 */
	private Long parentId;
	/**
	 * 根id
	 */
	private Long rootId;
	/**
	 * 层级
	 */
	private Byte level;
	/**
	 * 抓取类型
	 */
	private CrawlType crawlType;
	/**
	 * 抓取记录
	 */
	private CrawlRecord crawlRecord;
	/**
	 * 应用参数
	 */
	private Map<String, Object> appParams;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public CrawlRecord getCrawlRecord() {
		return crawlRecord;
	}

	public void setCrawlRecord(CrawlRecord crawlRecord) {
		this.crawlRecord = crawlRecord;
	}

	public Map<String, Object> getAppParams() {
		return appParams;
	}

	public void setAppParams(Map<String, Object> appParams) {
		this.appParams = appParams;
	}

	public Short getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Short websiteId) {
		this.websiteId = websiteId;
	}

	public Short getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Short applicationId) {
		this.applicationId = applicationId;
	}

	public CrawlType getCrawlType() {
		return crawlType;
	}

	public void setCrawlType(CrawlType crawlType) {
		this.crawlType = crawlType;
	}

	public Long getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(Long fingerprint) {
		this.fingerprint = fingerprint;
	}

	public Byte getPageType() {
		return pageType;
	}

	public void setPageType(Byte pageType) {
		this.pageType = pageType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
