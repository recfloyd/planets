package org.rec.planets.jupiter.action.network.cookie.bean;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.rec.planets.mercury.domain.AbstractBean;

public class ClientCookies extends AbstractBean {
	private static final long serialVersionUID = 3772852854072370864L;
	private final Set<HttpCookie> cookies;
	private final long timestamp;
	private AtomicInteger readCount;
	private volatile int writeCount;
	private final ReadWriteLock lock;

	public ClientCookies() {
		this(new LinkedList<HttpCookie>());
	}

	public ClientCookies(List<HttpCookie> cookies) {
		this.cookies = new HashSet<HttpCookie>(cookies);
		this.timestamp = System.currentTimeMillis();
		this.readCount = new AtomicInteger();
		this.writeCount = 1;
		this.lock = new ReentrantReadWriteLock();
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getReadCount() {
		return readCount.get();
	}

	public int getWriteCount() {
		return writeCount;
	}

	public void add(HttpCookie httpCookie) {
		lock.writeLock().lock();
		try {
			this.cookies.add(httpCookie);
			writeCount++;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void addAll(List<HttpCookie> cookies) {
		lock.writeLock().lock();
		try {
			this.cookies.addAll(cookies);
			writeCount++;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void removeByName(String name) {
		HttpCookie hc = null;
		lock.writeLock().lock();
		try {
			Iterator<HttpCookie> it = this.cookies.iterator();
			while (it.hasNext()) {
				hc = it.next();
				if (hc.getName().equalsIgnoreCase(name))
					it.remove();
			}
			writeCount++;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void removeAllByNames(Set<String> names) {
		HttpCookie hc = null;
		lock.writeLock().lock();
		try {
			Iterator<HttpCookie> it = this.cookies.iterator();
			while (it.hasNext()) {
				hc = it.next();
				if (names.contains(hc.getName()))
					it.remove();
			}
			writeCount++;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void clear() {
		lock.writeLock().lock();
		try {
			this.cookies.clear();
			writeCount++;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void retain(Set<String> names) {
		HttpCookie hc = null;
		lock.writeLock().lock();
		try {
			Iterator<HttpCookie> it = this.cookies.iterator();
			while (it.hasNext()) {
				hc = it.next();
				if (!names.contains(hc.getName()))
					it.remove();
			}
			writeCount++;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public List<HttpCookie> get() {
		lock.readLock().lock();
		List<HttpCookie> result = null;
		try {
			result = new ArrayList<HttpCookie>(this.cookies);
		} finally {
			lock.readLock().unlock();
		}
		readCount.incrementAndGet();
		return result;
	}
}
