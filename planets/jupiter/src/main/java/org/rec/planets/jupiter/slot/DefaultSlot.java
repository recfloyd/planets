package org.rec.planets.jupiter.slot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;
import org.rec.planets.jupiter.rule.Rule;
import org.rec.planets.jupiter.rule.RuleConstants;
import org.rec.planets.mercury.communication.bean.CanceledJob;
import org.rec.planets.mercury.communication.bean.CrawlEntity;
import org.rec.planets.mercury.communication.bean.CrawlPropagation;
import org.rec.planets.mercury.communication.bean.CurrentJob;
import org.rec.planets.mercury.communication.bean.JobResult;
import org.rec.planets.mercury.concurrent.PausableThreadPoolExecutor;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.domain.Job;

public class DefaultSlot implements Slot {
	private static final byte THREAD_PRIORITY_HIGH = 0;
	private static final byte THREAD_PRIORITY_MEDIUM = 50;

	private final Short websiteId;
	private AtomicReference<Rule> rule;
	private final PausableThreadPoolExecutor threadPool;
	private final ConcurrentMap<String, Object> slotContext;
	private final Map<Long, JobConter> jobCounters;

	public DefaultSlot(Short websiteId, Rule rule) {
		this.websiteId = websiteId;
		this.rule = new AtomicReference<Rule>(rule);
		int maxThread = RuleConstants.DEFAULT_MAX_THREAD;
		Map<String, Object> websiteProperties = rule.getWebsiteProperties();
		if (websiteProperties != null) {
			Integer i = (Integer) websiteProperties
					.get(RuleConstants.KEY_MAX_THREAD);
			if (i != null)
				maxThread = i;
		}
		threadPool = new PausableThreadPoolExecutor(maxThread, maxThread, 0,
				TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
		slotContext = new ConcurrentHashMap<String, Object>();
		jobCounters = new ConcurrentHashMap<Long, JobConter>();
	}

	/**
	 * 任务执行情况计数器
	 * 
	 * @author rec
	 * 
	 */
	private final class JobConter {
		private final int totalSize;
		private AtomicInteger todo;

		private JobConter(int totalSize) {
			this.totalSize = totalSize;
			this.todo = new AtomicInteger(totalSize);
		}
	}

	/**
	 * 具备执行优先级的线程
	 * 
	 * @author rec
	 * 
	 */
	private final class PriorityWorkingThread implements Runnable,
			Comparable<PriorityWorkingThread> {
		private final Long jobId;
		private final byte priority;
		private Action action;
		private ActionContext context;

		private PriorityWorkingThread(Long jobId, byte priority, Action action,
				ActionContext context) {
			this.jobId = jobId;
			this.priority = priority;
			this.action = action;
			this.context = context;
		}

		@Override
		public int compareTo(PriorityWorkingThread other) {
			return this.priority - other.priority;
		}

		@Override
		public void run() {
			try {
				action.execute(context);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				jobCounters.get(jobId).todo.decrementAndGet();
			}
		}
	}

	@Override
	public Short getWebsiteId() {
		return this.websiteId;
	}

	@Override
	public void pause() {
		this.threadPool.pause();
	}

	@Override
	public void resume() {
		this.threadPool.resume();
	}

	@Override
	public boolean isPaulsed() {
		return this.threadPool.isPaused();
	}

	@Override
	public int getMaxThread() {
		return this.threadPool.getMaximumPoolSize();
	}

	@Override
	public void submitJob(Job job) {
		Long id = job.getId();
		this.jobCounters.put(id, new JobConter(job.getUrls().size()));

		Rule rule = this.rule.get();
		Action action = rule.getAction();
		Map<String, Object> websiteProperties = rule.getWebsiteProperties();

		Runnable task = null;
		for (CrawlURL crawlURL : job.getUrls()) {
			task = new PriorityWorkingThread(id, THREAD_PRIORITY_MEDIUM,
					action, new ActionContext(crawlURL, websiteProperties,
							slotContext));
			this.threadPool.submit(task);
		}
	}

	private JobResult initEmptyJobResult(Job job) {
		JobResult jobResult = new JobResult();
		jobResult.setJobId(job.getId());
		// TODO:nodeId
		jobResult.setCreateTime(new Date());
		jobResult
				.setCrawlPropagations(new CopyOnWriteArrayList<CrawlPropagation>());
		jobResult.setCrawlEntities(new CopyOnWriteArrayList<CrawlEntity>());
		jobResult.setUnmodified(new CopyOnWriteArrayList<Long>());
		jobResult.setDisabled(new CopyOnWriteArrayList<Long>());
		jobResult.setDownloadFailed(new CopyOnWriteArrayList<Long>());
		jobResult.setProcessFailed(new CopyOnWriteArrayList<Long>());
		return jobResult;
	}

	@Override
	public JobResult runJob(Job job) {
		Long id = job.getId();
		this.jobCounters.put(id, new JobConter(job.getUrls().size()));

		Rule rule = this.rule.get();
		Action action = rule.getAction();
		Map<String, Object> websiteProperties = rule.getWebsiteProperties();

		JobResult jobResult = this.initEmptyJobResult(job);
		Runnable task = null;
		ActionContext context = null;
		List<Future<?>> futures = new ArrayList<Future<?>>();

		for (CrawlURL crawlURL : job.getUrls()) {
			context = new ActionContext(crawlURL, websiteProperties,
					slotContext);
			context.getUrlcontext().put(ActionContextConstants.KEY_JOB_RESULT,
					jobResult);
			task = new PriorityWorkingThread(id, THREAD_PRIORITY_HIGH, action,
					context);
			futures.add(this.threadPool.submit(task));
		}

		// 等待所有任务完成
		for (Future<?> future : futures) {
			try {
				future.get();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO 写日志
			}
		}

		return jobResult;
	}

	@Override
	public JobResult runJob(Job job, long timeout, TimeUnit timeUnit) {
		Long id = job.getId();
		this.jobCounters.put(id, new JobConter(job.getUrls().size()));

		Rule rule = this.rule.get();
		Action action = rule.getAction();
		Map<String, Object> websiteProperties = rule.getWebsiteProperties();

		JobResult jobResult = this.initEmptyJobResult(job);
		Runnable task = null;
		ActionContext context = null;
		Collection<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
		for (CrawlURL crawlURL : job.getUrls()) {
			context = new ActionContext(crawlURL, websiteProperties,
					slotContext);
			context.getUrlcontext().put(ActionContextConstants.KEY_JOB_RESULT,
					jobResult);
			task = new PriorityWorkingThread(id, THREAD_PRIORITY_HIGH, action,
					context);
			tasks.add(Executors.callable(task));
		}
		List<Future<Object>> futures = null;
		try {
			futures = this.threadPool.invokeAll(tasks, timeout, timeUnit);
		} catch (InterruptedException e) {
			e.printStackTrace();
			// TODO 写日志
			return null;
		}

		for (Future<Object> future : futures) {
			future.cancel(true);
		}

		this.threadPool.purge();

		return jobResult;
	}

	@Override
	public int getJobCount() {
		int jobCount = 0;
		for (Map.Entry<Long, JobConter> entry : jobCounters.entrySet()) {
			if (entry.getValue().todo.get() > 0)
				jobCount++;
		}
		return jobCount;
	}

	@Override
	public List<CurrentJob> getCurrentJob() {
		List<CurrentJob> result = new ArrayList<CurrentJob>();
		Set<Long> tobeDeleted = new HashSet<Long>();
		CurrentJob tmp = null;
		for (Map.Entry<Long, JobConter> entry : jobCounters.entrySet()) {
			tmp = new CurrentJob();
			tmp.setJobId(entry.getKey());
			tmp.setTotalSize(entry.getValue().totalSize);
			tmp.setTodoSize(entry.getValue().todo.get());
			result.add(tmp);

			if (tmp.getTodoSize() < 1) {// 如果任务已经完成,顺便删除掉
				tobeDeleted.add(entry.getKey());
			}
		}

		for (Long jobId : tobeDeleted) {
			this.jobCounters.remove(jobId);
		}

		return result;
	}

	@Override
	public void shutdown() {
		this.shutdown(Integer.MAX_VALUE, TimeUnit.DAYS);
	}

	@Override
	public void shutdown(long timeout, TimeUnit timeUnit) {
		this.threadPool.shutdown();
		try {
			this.threadPool.awaitTermination(timeout, timeUnit);
		} catch (InterruptedException e) {
			e.printStackTrace();
			// TODO 写日志
		}
	}

	/**
	 * 将已取消的任务封装为CanceledJob
	 * 
	 * @param runnables
	 * @return
	 */
	private List<CanceledJob> handleCanceledJob(List<Runnable> runnables) {
		List<CanceledJob> result = new ArrayList<CanceledJob>();
		Map<Long, List<CrawlURL>> jobs = new HashMap<Long, List<CrawlURL>>();

		PriorityWorkingThread task = null;
		List<CrawlURL> urls = null;
		CanceledJob canceledJob = null;
		for (Runnable thread : runnables) {
			task = (PriorityWorkingThread) thread;
			urls = jobs.get(task.jobId);
			if (urls == null) {
				urls = new ArrayList<CrawlURL>();
				jobs.put(task.jobId, urls);
				canceledJob = new CanceledJob();
				canceledJob.setJobId(task.jobId);
				canceledJob.setUrls(urls);
			}
			urls.add(task.context.getCrawlURL());
		}
		return result;
	}

	@Override
	public List<CanceledJob> cancelAll() {
		List<Runnable> canceled = new ArrayList<Runnable>();
		this.threadPool.getQueue().drainTo(canceled);
		return this.handleCanceledJob(canceled);
	}

	@Override
	public List<CanceledJob> shutdownNow() {
		List<Runnable> canceled = this.threadPool.shutdownNow();
		return this.handleCanceledJob(canceled);
	}

	@Override
	public Long getRuleVersion() {
		return this.rule.get().getVersion();
	}

	@Override
	public void setRule(Rule rule) {
		this.rule.set(rule);
	}

}
