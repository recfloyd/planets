package org.rec.planets.jupiter.slot.snapshot;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.rec.planets.mercury.communication.bean.CrawlEntity;
import org.rec.planets.mercury.communication.bean.CrawlPropagation;
import org.rec.planets.mercury.communication.bean.snapshot.JobResultSnapshot;

public class JobResultSnapshotFactory {
	private long lastSnapshotTime;
	private final Long jobId;
	private final ReadWriteLock lock;
	private final List<CrawlEntity> entities;
	private final List<CrawlPropagation> propagations;
	private final List<Long> unmodified;
	private final List<Long> disabled;
	private final List<Long> downloadFailed;
	private final List<Long> processFailed;

	public JobResultSnapshotFactory(Long jobId) {
		this.jobId = jobId;
		lastSnapshotTime = System.currentTimeMillis();
		this.lock = new ReentrantReadWriteLock();
		entities = Collections.synchronizedList(new LinkedList<CrawlEntity>());
		propagations = Collections
				.synchronizedList(new LinkedList<CrawlPropagation>());
		unmodified = Collections.synchronizedList(new LinkedList<Long>());
		disabled = Collections.synchronizedList(new LinkedList<Long>());
		downloadFailed = Collections.synchronizedList(new LinkedList<Long>());
		processFailed = Collections.synchronizedList(new LinkedList<Long>());
	}

	public void addEntity(CrawlEntity entity) {
		this.lock.readLock().lock();
		try {
			this.entities.add(entity);
		} finally {
			this.lock.readLock().unlock();
		}
	}

	public void addPropagation(CrawlPropagation propagation) {
		this.lock.readLock().lock();
		try {
			this.propagations.add(propagation);
		} finally {
			this.lock.readLock().unlock();
		}
	}

	public void addUnmodified(Long id) {
		this.lock.readLock().lock();
		try {
			this.unmodified.add(id);
		} finally {
			this.lock.readLock().unlock();
		}
	}

	public void addDisabled(Long id) {
		this.lock.readLock().lock();
		try {
			this.disabled.add(id);
		} finally {
			this.lock.readLock().unlock();
		}
	}

	public void addDownloadFailed(Long id) {
		this.lock.readLock().lock();
		try {
			this.downloadFailed.add(id);
		} finally {
			this.lock.readLock().unlock();
		}
	}

	public void addProcessFailed(Long id) {
		this.lock.readLock().lock();
		try {
			this.processFailed.add(id);
		} finally {
			this.lock.readLock().unlock();
		}
	}

	public JobResultSnapshot getSnapshot() {
		JobResultSnapshot snapshot = new JobResultSnapshot();
		snapshot.setJobId(jobId);
		snapshot.setSnapshotStart(lastSnapshotTime);
		long now = System.currentTimeMillis();
		snapshot.setSnapshotEnd(now);
		lastSnapshotTime = now;

		snapshot.setEntities(new LinkedList<CrawlEntity>());
		snapshot.setPropagations(new LinkedList<CrawlPropagation>());
		snapshot.setUnmodified(new LinkedList<Long>());
		snapshot.setDisabled(new LinkedList<Long>());
		snapshot.setDownloadFailed(new LinkedList<Long>());
		snapshot.setProcessFailed(new LinkedList<Long>());

		this.lock.writeLock().lock();
		try {
			snapshot.getEntities().addAll(entities);
			entities.clear();
			snapshot.getPropagations().addAll(propagations);
			propagations.clear();
			snapshot.getUnmodified().addAll(unmodified);
			unmodified.clear();
			snapshot.getDisabled().addAll(disabled);
			disabled.clear();
			snapshot.getDownloadFailed().addAll(downloadFailed);
			downloadFailed.clear();
			snapshot.getProcessFailed().addAll(processFailed);
			processFailed.clear();
		} finally {
			this.lock.writeLock().unlock();
		}

		return snapshot;
	}
}
