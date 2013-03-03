package org.rec.planets.mercury.communication.bean;

import java.io.Serializable;

public class PlainCurrentJob implements CurrentJob, Serializable {
	private static final long serialVersionUID = -6596317630245666423L;
	private Long jobId;
	private int totalSize;
	private int todoSize;

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public void setTodoSize(int todoSize) {
		this.todoSize = todoSize;
	}

	@Override
	public Long getJobId() {
		return this.jobId;
	}

	@Override
	public int getTotalSize() {
		return this.totalSize;
	}

	@Override
	public int getTodoSize() {
		return this.todoSize;
	}

}
