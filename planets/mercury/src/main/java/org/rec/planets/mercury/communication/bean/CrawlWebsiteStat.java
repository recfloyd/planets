package org.rec.planets.mercury.communication.bean;

import java.io.Serializable;
import java.util.Date;

import org.rec.planets.mercury.domain.AbstractBean;

/**
 * 抓取过程中的网站信息统计
 * 
 * @author lijia
 * 
 */
public class CrawlWebsiteStat extends AbstractBean  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 网站id
	 */
	private Short websiteId;
	/**
	 * 开始统计时间
	 */
	private Date statStart;
	/**
	 * 结束统计时间
	 */
	private Date statEnd;
	/**
	 * 请求次数
	 */
	private int requestCount;
	/**
	 * 请求字节数
	 */
	private long requestByte;
	/**
	 * 请求毫秒数
	 */
	private long requestMS;

	public Short getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Short websiteId) {
		this.websiteId = websiteId;
	}

	public Date getStatStart() {
		return statStart;
	}

	public void setStatStart(Date statStart) {
		this.statStart = statStart;
	}

	public Date getStatEnd() {
		return statEnd;
	}

	public void setStatEnd(Date statEnd) {
		this.statEnd = statEnd;
	}

	public int getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}

	public long getRequestByte() {
		return requestByte;
	}

	public void setRequestByte(long requestByte) {
		this.requestByte = requestByte;
	}

	public long getRequestMS() {
		return requestMS;
	}

	public void setRequestMS(long requestMS) {
		this.requestMS = requestMS;
	}
}
