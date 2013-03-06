package org.rec.planets.jupiter.slot.snapshot;

import java.util.concurrent.atomic.AtomicInteger;

import org.rec.planets.mercury.communication.bean.snapshot.CurrentJobSnapshot;

/**
 * 当前任务快照生成器
 * 
 * @author rec
 * 
 */
public class CurrentJobSnapshotFactory {
	private final Long jobId;
	private final int totalSize;
	private final long startTime;

	private long lastSnapshotTime;
	private final AtomicInteger todoSize;

	public CurrentJobSnapshotFactory(Long jobId, int totalSize) {
		this.jobId = jobId;
		this.totalSize = totalSize;
		this.startTime = System.currentTimeMillis();
		lastSnapshotTime = this.startTime;
		todoSize = new AtomicInteger(totalSize);
	}

	/**
	 * 搞定一个url
	 */
	public int doneOne() {
		return todoSize.decrementAndGet();
	}

	public int getTodoSize() {
		return todoSize.get();
	}

	/**
	 * 输出一个快照.假定此操作只有单线程执行
	 * 
	 * @return
	 */
	public CurrentJobSnapshot getSnapshot() {
		CurrentJobSnapshot snapshot = new CurrentJobSnapshot();
		snapshot.setJobId(jobId);
		snapshot.setTotalSize(totalSize);
		snapshot.setStartTime(startTime);
		snapshot.setSnapshotStart(lastSnapshotTime);

		long now = System.currentTimeMillis();
		snapshot.setSnapshotEnd(now);
		lastSnapshotTime = now;
		snapshot.setTodoSize(todoSize.get());

		return snapshot;
	}
}
