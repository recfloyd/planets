package org.rec.planets.jupiter.action.workflow.parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.rec.planets.jupiter.context.ActionContext;

/**
 * 一个全局共享的线程池工厂
 * 
 * @author rec
 * 
 */
public class GlobalThreadPoolFactory implements ThreadPoolFactory {
	private static volatile ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
			10, 10, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());;
	@SuppressWarnings("unused")
	private int maxThread = 10;

	@Override
	public ExecutorService getThreadPool(ActionContext context) {
		return threadPool;
	}

	public void setMaxThread(int maxThread) {
		this.maxThread = maxThread;
		if (threadPool == null) {
			synchronized (GlobalThreadPoolFactory.class) {
				if (threadPool == null) {
					threadPool = new ThreadPoolExecutor(maxThread, maxThread,
							0L, TimeUnit.MILLISECONDS,
							new LinkedBlockingQueue<Runnable>());
				} else {
					threadPool.setCorePoolSize(maxThread);
					threadPool.setMaximumPoolSize(maxThread);
				}
			}
		} else {
			threadPool.setCorePoolSize(maxThread);
			threadPool.setMaximumPoolSize(maxThread);
		}
	}

}
