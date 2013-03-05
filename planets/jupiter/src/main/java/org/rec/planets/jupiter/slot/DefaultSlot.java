package org.rec.planets.jupiter.slot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;
import org.rec.planets.jupiter.rule.Rule;
import org.rec.planets.jupiter.rule.RuleConstants;
import org.rec.planets.jupiter.slot.DefaultSlotFactory.RuleVersionHook;
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
	private final ConcurrentMap<Long, AtomicInteger> versionCounters;
	private final RuleVersionHook hook;

	public DefaultSlot(Rule rule, RuleVersionHook hook) {
		this.websiteId = rule.getWebsiteId();
		this.rule = new AtomicReference<Rule>(rule);
		this.hook = hook;
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
		versionCounters = new ConcurrentHashMap<Long, AtomicInteger>();
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
	 * 具备执行优先级的FutureTask,所有任务转化为此类的实例,并且向线程池提交时必须使用execute方法,否则其队列优先级无法保证
	 * 
	 * @author rec
	 * 
	 */
	private final class PriorityFutureTask extends FutureTask<Void> implements
			Comparable<PriorityFutureTask> {
		private final Long jobId;
		private final byte priority;
		private final CrawlURL crawlURL;

		private PriorityFutureTask(final Long jobId, final byte priority,
				final Action action, final ActionContext context,
				final Long version) {
			super(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					try {
						action.execute(context);
						return null;
					} finally {
						if (jobCounters.get(jobId).todo.decrementAndGet() <= 0) {
							// TODO: 一个任务已经完成
							if (versionCounters.get(version).decrementAndGet() <= 0) {
								if (!version.equals(rule.get().getVersion())) {
									// TODO: 一个旧版本的规则已经不再被引用,这时候删除并销毁它
									versionCounters.remove(version);
									hook.destroyRuleVersion(websiteId, version);
								}
							}
						}
					}
				}
			});
			this.jobId = jobId;
			this.priority = priority;
			this.crawlURL = context.getCrawlURL();
		}

		@Override
		public int compareTo(PriorityFutureTask other) {
			return this.priority - other.priority;
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
		List<CrawlURL> urls = job.getUrls();
		this.jobCounters.put(id, new JobConter(urls.size()));

		Rule rule = this.rule.get();
		Action action = rule.getAction();
		Map<String, Object> websiteProperties = rule.getWebsiteProperties();
		Long version = rule.getVersion();
		this.versionCounters.putIfAbsent(version, new AtomicInteger(0));
		this.versionCounters.get(version).incrementAndGet();

		PriorityFutureTask task = null;
		for (CrawlURL crawlURL : urls) {
			task = new PriorityFutureTask(
					id,
					THREAD_PRIORITY_MEDIUM,
					action,
					new ActionContext(crawlURL, websiteProperties, slotContext),
					version);
			this.threadPool.execute(task);
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
		List<CrawlURL> urls = job.getUrls();
		this.jobCounters.put(id, new JobConter(urls.size()));

		Rule rule = this.rule.get();
		Action action = rule.getAction();
		Map<String, Object> websiteProperties = rule.getWebsiteProperties();
		Long version = rule.getVersion();
		this.versionCounters.putIfAbsent(version, new AtomicInteger(0));
		this.versionCounters.get(version).incrementAndGet();

		JobResult jobResult = this.initEmptyJobResult(job);
		PriorityFutureTask task = null;
		ActionContext context = null;
		List<PriorityFutureTask> futures = new ArrayList<PriorityFutureTask>(
				urls.size());

		for (CrawlURL crawlURL : urls) {
			context = new ActionContext(crawlURL, websiteProperties,
					slotContext);
			context.getUrlcontext().put(ActionContextConstants.KEY_JOB_RESULT,
					jobResult);
			task = new PriorityFutureTask(id, THREAD_PRIORITY_HIGH, action,
					context, version);
			futures.add(task);
			this.threadPool.execute(task);
		}

		// 等待所有任务完成
		for (Future<Void> future : futures) {
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
		List<CrawlURL> urls = job.getUrls();
		this.jobCounters.put(id, new JobConter(urls.size()));

		Rule rule = this.rule.get();
		Action action = rule.getAction();
		Map<String, Object> websiteProperties = rule.getWebsiteProperties();
		Long version = rule.getVersion();
		this.versionCounters.putIfAbsent(version, new AtomicInteger(0));
		this.versionCounters.get(version).incrementAndGet();

		JobResult jobResult = this.initEmptyJobResult(job);
		PriorityFutureTask task = null;
		ActionContext context = null;
		List<PriorityFutureTask> futures = new ArrayList<PriorityFutureTask>(
				urls.size());

		for (CrawlURL crawlURL : urls) {
			context = new ActionContext(crawlURL, websiteProperties,
					slotContext);
			context.getUrlcontext().put(ActionContextConstants.KEY_JOB_RESULT,
					jobResult);
			task = new PriorityFutureTask(id, THREAD_PRIORITY_HIGH, action,
					context, version);
			futures.add(task);
		}

		long nanos = timeUnit.toNanos(timeout);
		boolean done = false;
		try {

			long lastTime = System.nanoTime();
			// Interleave time checks and calls to execute in case
			// executor doesn't have any/much parallelism.
			Iterator<PriorityFutureTask> it = futures.iterator();
			while (it.hasNext()) {
				this.threadPool.execute(it.next());
				long now = System.nanoTime();
				nanos -= now - lastTime;
				lastTime = now;
				if (nanos <= 0)
					return jobResult;
			}

			for (PriorityFutureTask f : futures) {
				if (!f.isDone()) {
					if (nanos <= 0)
						return jobResult;
					try {
						f.get(nanos, TimeUnit.NANOSECONDS);
					} catch (InterruptedException ignore) {
					} catch (CancellationException ignore) {
					} catch (ExecutionException ignore) {
					} catch (TimeoutException toe) {
						return jobResult;
					}
					long now = System.nanoTime();
					nanos -= now - lastTime;
					lastTime = now;
				}
			}
			done = true;
			return jobResult;
		} finally {
			if (!done) {
				for (PriorityFutureTask f : futures)
					f.cancel(true);
				this.threadPool.purge();
			}
		}

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

		PriorityFutureTask task = null;
		List<CrawlURL> urls = null;
		CanceledJob canceledJob = null;
		for (Runnable thread : runnables) {
			task = (PriorityFutureTask) thread;
			urls = jobs.get(task.jobId);
			if (urls == null) {
				urls = new ArrayList<CrawlURL>();
				jobs.put(task.jobId, urls);
				canceledJob = new CanceledJob();
				canceledJob.setJobId(task.jobId);
				canceledJob.setUrls(urls);
			}
			urls.add(task.crawlURL);
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
