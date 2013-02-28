package org.rec.planets.jupiter.slot;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.rec.planets.mercury.communication.bean.JobResult;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.domain.Job;

/**
 * 任务执行槽
 * 
 * @author rec
 * 
 */
public interface Slot {
	/**
	 * 当前正在执行的任务
	 * @author rec
	 *
	 */
	interface CurrentJob {
		Long getCurrentJobId();

		void getTodoSize();
	}

	/**
	 * 被取消的任务
	 * @author rec
	 *
	 */
	interface CanceledJob {
		Long getJobId();

		List<CrawlURL> getCrawlURLs();
	}

	/**
	 * 网站id
	 * @return
	 */
	Short getWebsiteId();

	/**
	 * 暂停
	 */
	void pause();

	/**
	 * 恢复
	 */
	void resume();
	
	/**
	 * 是否暂停状态
	 * @return
	 */
	boolean isPaulsed();

	/**
	 * 返回最大线程数
	 * @return
	 */
	int getMaxThread();

	/**
	 * 设置最大线程数
	 * @param maxThread
	 */
	void setMaxThread(int maxThread);

	/**
	 * 提交一个任务
	 * @param job
	 */
	void submitJob(Job job);

	/**
	 * 同步执行一个任务
	 * @param job
	 * @return
	 */
	JobResult runJob(Job job);

	/**
	 * 同步执行一个任务,如果任务超时则取消
	 * @param job
	 * @param timeout
	 * @param timeUnit
	 * @return
	 */
	JobResult runJob(Job job, long timeout, TimeUnit timeUnit);

	/**
	 * 返回任务总数
	 * @return
	 */
	int getJobCount();

	/**
	 * 返回当前运行的任务的情况
	 * @return
	 */
	CurrentJob getCurrentJob();

	/**
	 * 平滑关闭任务槽
	 */
	void shutdown();

	/**
	 * 在时间限制内,平滑关闭任务槽
	 * @param timeout
	 * @param timeUnit
	 */
	void shutdown(long timeout, TimeUnit timeUnit);
	
	/**
	 * 取消所有等待执行的任务
	 * @return
	 */
	List<CanceledJob> cancelAll();

	/**
	 * 立即关闭任务槽,并将尚未执行的任务返回
	 * @return
	 */
	List<CanceledJob> shutdownNow();
}
