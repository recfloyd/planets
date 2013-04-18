package org.rec.planets.jupiter.action.workflow.iterable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.workflow.iterable.bean.IterableItem;
import org.rec.planets.jupiter.action.workflow.iterable.bean.IterableItemStackHolder;
import org.rec.planets.jupiter.action.workflow.parallel.ThreadPoolFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 抽象的循环处理器 此处理器通过getItems获取一个可以遍历的对象
 * 在遍历此对象的过程中,将每次遍历的实体封装为IterableItem对象保存在当前线程的ThreadLocal内
 * 对于每次遍历,执行一个内置的Processor的处理.
 * 此内置的Processor如果需要访问当前遍历实体,可以从IterableItemStackHolder内获取;
 * 如果需要对CrawlContext进行循环读写,那么需要选用一个合适的CrawlContextAccessor
 * 
 * 此处理器还支持并发处理,注入一个线程池并将parallel设置为true,可以并行的进行遍历
 * 
 * 注意,遍历过程中如果需要访问遍历实体,那么必须在执行内置Processor的线程内进行,如果另外开启线程,因为访问不到IterableItemStack
 * 
 * @author rec
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractIterableAction implements Action {
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractIterableAction.class);

	protected abstract Object getItems(ActionContext context);

	protected Action nestedAction;
	protected boolean omitAbsence;
	protected boolean parallel;
	protected boolean omitException;
	protected ThreadPoolFactory threadPoolFactory;

	@Override
	public void execute(ActionContext context) throws Exception {
		Object object = getItems(context);
		if (object == null) {
			if (omitAbsence)
				return;
			else
				throw new IllegalArgumentException(
						"cannot get iterable items of crawlURL "
								+ context.getCrawlURL());
		}

		List<IterableItem> list = null;

		Class clazz = object.getClass();

		if (Map.class.isAssignableFrom(clazz)) {
			list = traverseMap(object, context);
		} else if (Collection.class.isAssignableFrom(clazz)) {
			list = traverseCollection(object, context);
		} else if (clazz.isArray()) {
			list = traverseArray(object, context);
		} else {
			throw new IllegalArgumentException(
					"items are not iterable , class " + clazz + " crawlURL "
							+ context.getCrawlURL());
		}

		if (parallel) {
			Assert.notNull(threadPoolFactory,
					"property threadPoolFactory is not configed but parallel is true");

			ExecutorService threadPool = threadPoolFactory
					.getThreadPool(context);
			List<Future<Void>> reaults = new ArrayList<Future<Void>>(
					list.size());
			IterableActionFutureTask task = null;
			for (IterableItem item : list) {
				task = new IterableActionFutureTask(nestedAction, context, item);
				threadPool.execute(task);
				reaults.add(task);
			}
			for (int i = 0; i < reaults.size(); i++) {
				try {
					reaults.get(i).get();
				} catch (ExecutionException e) {
					logger.warn("error occured when process crawlURL: "
							+ context.getCrawlURL() + " and action index is "
							+ i + " and action is " + nestedAction, e);
					if (omitException) {
						logger.info("exception ignored");
						continue;
					} else {
						throw e;
					}
				}
			}
		} else {
			IterableItem item = null;
			for (int i = 0; i < list.size(); i++) {
				item = list.get(i);
				IterableItemStackHolder.putItem(item);
				try {
					nestedAction.execute(context);
				} catch (Exception e) {
					logger.warn(
							"error occured and omitted when process crawlURL: "
									+ context.getCrawlURL()
									+ " and action index is " + i
									+ " and action is " + nestedAction, e);
					if (omitException) {
						logger.info("exception ignored");
						continue;
					} else {
						throw e;
					}
				} finally {
					IterableItemStackHolder.popItem();
				}
			}
		}
	}

	protected List<IterableItem> traverseMap(Object object,
			ActionContext context) throws Exception {
		Map map = (Map) object;
		int index = 0;
		int total = map.size();
		IterableItem item = null;
		List<IterableItem> list = new ArrayList<IterableItem>(total);
		for (Map.Entry entry : (Set<Map.Entry>) map.entrySet()) {
			item = new IterableItem(entry, index, total, index < total - 1,
					index == 0, index == total - 1);
			index++;
			list.add(item);
		}
		return list;
	}

	protected List<IterableItem> traverseCollection(Object object,
			ActionContext context) throws Exception {
		Collection collection = (Collection) object;
		int index = 0;
		int total = collection.size();
		IterableItem item = null;
		List<IterableItem> list = new ArrayList<IterableItem>(total);
		for (Object obj : collection) {
			item = new IterableItem(obj, index, total, index < total - 1,
					index == 0, index == total - 1);
			index++;
			list.add(item);
		}
		return list;
	}

	protected List<IterableItem> traverseArray(Object object,
			ActionContext context) throws Exception {
		int total = Array.getLength(object);
		IterableItem item = null;
		List<IterableItem> list = new ArrayList<IterableItem>(total);
		for (int i = 0; i < total; i++) {
			item = new IterableItem(Array.get(object, i), i, total,
					i < total - 1, i == 0, i == total - 1);
			list.add(item);
		}
		return list;
	}

	public void setNestedAction(Action nestedAction) {
		this.nestedAction = nestedAction;
	}

	public void setOmitAbsence(boolean omitAbsence) {
		this.omitAbsence = omitAbsence;
	}

	public void setParallel(boolean parallel) {
		this.parallel = parallel;
	}

	public void setOmitException(boolean omitException) {
		this.omitException = omitException;
	}

	public void setThreadPoolFactory(ThreadPoolFactory threadPoolFactory) {
		this.threadPoolFactory = threadPoolFactory;
	}

}
