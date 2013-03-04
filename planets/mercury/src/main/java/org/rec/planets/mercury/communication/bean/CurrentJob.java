package org.rec.planets.mercury.communication.bean;

import java.io.Serializable;

/**
 * 当前正在执行的任务
 * 
 * @author rec
 * 
 */
public class CurrentJob implements Serializable {
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

	public Long getJobId() {
		return this.jobId;
	}

	public int getTotalSize() {
		return this.totalSize;
	}

	public int getTodoSize() {
		return this.todoSize;
	}
}
