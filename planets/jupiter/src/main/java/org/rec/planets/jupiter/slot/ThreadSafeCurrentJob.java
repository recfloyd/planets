package org.rec.planets.jupiter.slot;

import java.util.concurrent.atomic.AtomicInteger;

import org.rec.planets.mercury.communication.bean.CurrentJob;

public class ThreadSafeCurrentJob implements CurrentJob {
	private final Long jobId;
	private final int totalSize;
	private AtomicInteger todoSize;

	public ThreadSafeCurrentJob(Long jobId, int totalSize) {
		this.jobId = jobId;
		this.totalSize = totalSize;
		this.todoSize = new AtomicInteger(totalSize);
	}

	@Override
	public Long getJobId() {
		return jobId;
	}

	@Override
	public int getTotalSize() {
		return totalSize;
	}

	@Override
	public int getTodoSize() {
		return todoSize.get();
	}

	public void doOne() {
		this.todoSize.decrementAndGet();
	}
}
