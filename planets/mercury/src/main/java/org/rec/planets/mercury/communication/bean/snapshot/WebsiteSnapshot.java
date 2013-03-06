package org.rec.planets.mercury.communication.bean.snapshot;

import java.io.Serializable;


/**
 * 网站统计用的快照
 * 
 * @author rec
 * 
 */
public class WebsiteSnapshot extends AbstractSnapshot implements Serializable {
	private static final long serialVersionUID = -4671881348114814626L;
	private Short websiteId;
	private int requestCount;
	private long requestByte;
	private long requestMS;

	public Short getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Short websiteId) {
		this.websiteId = websiteId;
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
