package org.rec.planets.mercury.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 抓取记录
 * 
 * @author rec
 * 
 */
public class CrawlRecord implements Serializable {
	private static final long serialVersionUID = 5610148844433356217L;
	/**
	 * 上次抓取时间
	 */
	private Date lastTime;
	/**
	 * 下次抓取时间
	 */
	private Date nextTime;
	/**
	 * 上次抓取节点
	 */
	private Integer lastNodeId;
	/**
	 * 爬虫状态
	 */
	private Byte crawlStatus;
	/**
	 * 抓取参数
	 */
	private Map<String, Object> crawlParams;

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getNextTime() {
		return nextTime;
	}

	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}

	public Integer getLastNodeId() {
		return lastNodeId;
	}

	public void setLastNodeId(Integer lastNodeId) {
		this.lastNodeId = lastNodeId;
	}

	public Byte getCrawlStatus() {
		return crawlStatus;
	}

	public void setCrawlStatus(Byte crawlStatus) {
		this.crawlStatus = crawlStatus;
	}

	public Map<String, Object> getCrawlParams() {
		return crawlParams;
	}

	public void setCrawlParams(Map<String, Object> crawlParams) {
		this.crawlParams = crawlParams;
	}
}
