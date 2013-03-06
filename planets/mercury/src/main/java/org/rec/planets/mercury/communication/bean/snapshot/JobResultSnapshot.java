package org.rec.planets.mercury.communication.bean.snapshot;

import java.io.Serializable;
import java.util.List;

import org.rec.planets.mercury.communication.bean.CrawlEntity;
import org.rec.planets.mercury.communication.bean.CrawlPropagation;

public class JobResultSnapshot extends AbstractSnapshot implements Serializable {
	private static final long serialVersionUID = 7160664158192174916L;
	private Long jobId;
	private List<CrawlEntity> entities;
	private List<CrawlPropagation> propagations;
	private List<Long> unmodified;
	private List<Long> disabled;
	private List<Long> downloadFailed;
	private List<Long> processFailed;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public List<CrawlEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<CrawlEntity> entities) {
		this.entities = entities;
	}

	public List<CrawlPropagation> getPropagations() {
		return propagations;
	}

	public void setPropagations(List<CrawlPropagation> propagations) {
		this.propagations = propagations;
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
