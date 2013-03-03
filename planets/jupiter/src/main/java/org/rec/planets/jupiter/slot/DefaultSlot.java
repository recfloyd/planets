package org.rec.planets.jupiter.slot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import org.rec.planets.mercury.communication.bean.PlainCurrentJob;
import org.rec.planets.mercury.concurrent.PausableThreadPoolExecutor;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.domain.Job;

public class DefaultSlot implements Slot {
	private static final byte THREAD_PRIORITY_HIGH = 0;
	private static final byte THREAD_PRIORITY_MEDIUM = 50;

	private final Short websiteId;
	private Rule rule;
	private final PausableThreadPoolExecutor threadPool;
	private final ConcurrentMap<String, Object> slotContext;
	private final Map<Long, ThreadSafeCurrentJob> currentJobs;

	public DefaultSlot(Short websiteId, Rule rule) {
		this.websiteId = websiteId;
		this.rule = rule;
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
		currentJobs = new ConcurrentHashMap<Long, ThreadSafeCurrentJob>();
	}

	/**
	 * 具备执行优先级的线程
	 * 
	 * @author rec
	 * 
	 */
	public static final class PriorityWorkingThread implements Runnable,
			Comparable<PriorityWorkingThread> {
		private byte priority;
		private Action action;
		private ActionContext context;

		private PriorityWorkingThread(byte priority, Action action,
				ActionContext context) {
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
		ThreadSafeCurrentJob currentJob = new ThreadSafeCurrentJob(job.getId(),
				job.getUrls().size());
		this.currentJobs.put(job.getId(), currentJob);
		Runnable task = null;
		Action action = this.rule.getAction();
		Map<String, Object> websiteProperties = this.rule
				.getWebsiteProperties();
		for (CrawlURL crawlURL : job.getUrls()) {
			task = new PriorityWorkingThread(THREAD_PRIORITY_MEDIUM, action,
					new ActionContext(crawlURL, websiteProperties, slotContext,
							currentJob));
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
		ThreadSafeCurrentJob currentJob = new ThreadSafeCurrentJob(job.getId(),
				job.getUrls().size());
		this.currentJobs.put(job.getId(), currentJob);
		JobResult jobResult = this.initEmptyJobResult(job);
		Runnable task = null;
		ActionContext context = null;
		Action action = this.rule.getAction();
		Map<String, Object> websiteProperties = this.rule
				.getWebsiteProperties();
		List<Future<?>> futures=new ArrayList<Future<?>>();
		for (CrawlURL crawlURL : job.getUrls()) {
			context=new ActionContext(crawlURL, websiteProperties, slotContext,
					currentJob);
			context.getUrlcontext().put(ActionContextConstants.KEY_JOB_RESULT,jobResult);
			task=new PriorityWorkingThread(THREAD_PRIORITY_HIGH, action, context);
			futures.add(this.threadPool.submit(task));
		}
		
		//等待所有任务完成
		for(Future<?> future:futures){
			try {
				future.get();
			} catch (Exception e) {
				e.printStackTrace();
				//TODO 写日志
			}
		}
		
		return jobResult;
	}

	@Override
	public JobResult runJob(Job job, long timeout, TimeUnit timeUnit) {
		ThreadSafeCurrentJob currentJob = new ThreadSafeCurrentJob(job.getId(),
				job.getUrls().size());
		this.currentJobs.put(job.getId(), currentJob);
		JobResult jobResult = this.initEmptyJobResult(job);
		Runnable task = null;
		ActionContext context = null;
		Action action = this.rule.getAction();
		Map<String, Object> websiteProperties = this.rule
				.getWebsiteProperties();
		Collection<Callable<Object>> tasks=new ArrayList<Callable<Object>>();
		for (CrawlURL crawlURL : job.getUrls()) {
			context=new ActionContext(crawlURL, websiteProperties, slotContext,
					currentJob);
			context.getUrlcontext().put(ActionContextConstants.KEY_JOB_RESULT,jobResult);
			task=new PriorityWorkingThread(THREAD_PRIORITY_HIGH, action, context);
			tasks.add(Executors.callable(task));
		}
		List<Future<Object>> futures=null;
		try {
			futures = this.threadPool.invokeAll(tasks,timeout,timeUnit);
		} catch (InterruptedException e) {
			e.printStackTrace();
			//TODO 写日志
			return null;
		}
		
		for(Future<Object> future:futures){
			future.cancel(true);
		}
		
		this.threadPool.purge();
		
		return jobResult;
	}

	@Override
	public int getJobCount() {
		int jobCount = 0;
		for (Map.Entry<Long, ThreadSafeCurrentJob> entry : currentJobs
				.entrySet()) {
			if (entry.getValue().getTodoSize() > 0)
				jobCount++;
		}
		return jobCount;
	}

	@Override
	public List<CurrentJob> getCurrentJob() {
		List<CurrentJob> result = new ArrayList<CurrentJob>();
		Set<Long> tobeDeleted = new HashSet<Long>();
		CurrentJob stored = null;
		PlainCurrentJob tmp = null;
		for (Map.Entry<Long, ThreadSafeCurrentJob> entry : currentJobs
				.entrySet()) {
			stored = entry.getValue();
			tmp = new PlainCurrentJob();
			tmp.setJobId(stored.getJobId());
			tmp.setTotalSize(stored.getTotalSize());
			tmp.setTodoSize(stored.getTodoSize());
			result.add(tmp);

			if (tmp.getTodoSize() < 1) {// 如果任务已经完成,顺便删除掉
				tobeDeleted.add(entry.getKey());
			}
		}

		for (Long jobId : tobeDeleted) {
			this.currentJobs.remove(jobId);
		}

		return result;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown(long timeout, TimeUnit timeUnit) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CanceledJob> cancelAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CanceledJob> shutdownNow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getRuleVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRule(Rule rule) {
		// TODO Auto-generated method stub

	}

}
