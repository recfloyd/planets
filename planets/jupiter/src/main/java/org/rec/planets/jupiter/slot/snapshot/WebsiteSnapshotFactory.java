package org.rec.planets.jupiter.slot.snapshot;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.rec.planets.mercury.communication.bean.snapshot.WebsiteSnapshot;

public class WebsiteSnapshotFactory {
	private long lastSnapshotTime;
	private final Short websiteId;
	private final AtomicInteger requestCount;
	private final AtomicLong requestByte;
	private final AtomicLong requestMS;
	private final ReadWriteLock lock;

	public WebsiteSnapshotFactory(Short websiteId) {
		this.websiteId = websiteId;
		lastSnapshotTime = System.currentTimeMillis();
		this.requestCount = new AtomicInteger();
		this.requestByte = new AtomicLong();
		this.requestMS = new AtomicLong();
		this.lock = new ReentrantReadWriteLock();
	}

	/**
	 * 记录一次请求,此操作使用共享锁,因为其要改变的每一个数据结构都是线程安全的
	 * 
	 * @param requestByte
	 * @param requestMS
	 */
	public void record(long requestByte, long requestMS) {
		this.lock.readLock().lock();
		try {
			this.requestByte.addAndGet(requestByte);
			this.requestMS.addAndGet(requestMS);
			this.requestCount.incrementAndGet();
		} finally {
			this.lock.readLock().unlock();
		}
	}

	/**
	 * 生成快照,此操作需要独占锁,为了减少独占锁的容量,简化为:此操作只有单线程执行
	 * 
	 * @return
	 */
	public WebsiteSnapshot getsSnapshot() {
		WebsiteSnapshot snapshot = new WebsiteSnapshot();
		snapshot.setWebsiteId(websiteId);
		snapshot.setSnapshotStart(lastSnapshotTime);
		long now = System.currentTimeMillis();
		snapshot.setSnapshotEnd(now);
		lastSnapshotTime = now;

		this.lock.writeLock().lock();
		try {
			snapshot.setRequestByte(this.requestByte.getAndSet(0));
			snapshot.setRequestMS(this.requestMS.getAndSet(0));
			snapshot.setRequestCount(this.requestCount.getAndSet(0));
		} finally {
			this.lock.writeLock().unlock();
		}

		return snapshot;
	}
}
