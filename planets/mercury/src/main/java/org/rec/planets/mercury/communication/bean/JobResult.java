package org.rec.planets.mercury.communication.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.rec.planets.mercury.domain.AbstractBean;

public class JobResult extends AbstractBean  implements Serializable {
	private static final long serialVersionUID = 8563537409942143078L;
	private Long jobId;
	private Short nodeId;
	private Date createTime;
	private List<CrawlPropagation> crawlPropagations;// 衍生的url
	private List<CrawlEntity> crawlEntities;// 抽取的实体
	private List<Long> unmodified;// 内容未改变的url
	private List<Long> disabled;// 已失效的url
	private List<Long> downloadFailed;// 下载失败的url
	private List<Long> processFailed;// 处理失败的url

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Short getNodeId() {
		return nodeId;
	}

	public void setNodeId(Short nodeId) {
		this.nodeId = nodeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<CrawlPropagation> getCrawlPropagations() {
		return crawlPropagations;
	}

	public void setCrawlPropagations(List<CrawlPropagation> crawlPropagations) {
		this.crawlPropagations = crawlPropagations;
	}

	public List<CrawlEntity> getCrawlEntities() {
		return crawlEntities;
	}

	public void setCrawlEntities(List<CrawlEntity> crawlEntities) {
		this.crawlEntities = crawlEntities;
	}

	public List<Long> getUnmodified() {
		return unmodified;
	}

	public void setUnmodified(List<Long> unmodified) {
		this.unmodified = unmodified;
	}

	public List<Long> getDisabled() {
		return disabled;
	}

	public void setDisabled(List<Long> disabled) {
		this.disabled = disabled;
	}

	public List<Long> getDownloadFailed() {
		return downloadFailed;
	}

	public void setDownloadFailed(List<Long> downloadFailed) {
		this.downloadFailed = downloadFailed;
	}

	public List<Long> getProcessFailed() {
		return processFailed;
	}

	public void setProcessFailed(List<Long> processFailed) {
		this.processFailed = processFailed;
	}
}
