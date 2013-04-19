package org.rec.planets.jupiter.context.accessor.writer;

import java.util.concurrent.ConcurrentHashMap;

public abstract class ThreadLocalObjectHolder {
	private static final ConcurrentHashMap<String, ThreadLocal<Object>> cache = new ConcurrentHashMap<String, ThreadLocal<Object>>();

	public static final boolean contains(String key) {
		return cache.containsKey(key);
	}

	public static final void atomicInit(String key) {
		cache.putIfAbsent(key, new ThreadLocal<Object>());
	}

	public static final void putObject(String key, Object obj) {
		ThreadLocal<Object> threadLocal = cache.get(key);
		if (threadLocal == null) {
			atomicInit(key);
			threadLocal = cache.get(key);
		}
		threadLocal.set(obj);
	}

	public static final Object getObject(String key) {
		ThreadLocal<Object> threadLocal = cache.get(key);
		return threadLocal == null ? null : threadLocal.get();
	}

	public static final Object removeObject(String key) {
		ThreadLocal<Object> threadLocal = cache.get(key);
		if (threadLocal != null) {
			Object result = threadLocal.get();
			threadLocal.remove();
			return result;
		} else
			return null;
	}

	public static final void clear(String key) {
		cache.remove(key);
	}
}
