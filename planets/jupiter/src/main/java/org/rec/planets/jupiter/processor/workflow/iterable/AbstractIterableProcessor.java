package org.rec.planets.jupiter.processor.workflow.iterable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.processor.Action;
import org.rec.planets.jupiter.processor.workflow.iterable.bean.IterableItem;
import org.rec.planets.jupiter.processor.workflow.iterable.bean.IterableItemStackHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 抽象的循环处理器
 * 此处理器通过getItems获取一个可以遍历的对象
 * 在遍历此对象的过程中,将每次遍历的实体封装为IterableItem对象保存在当前线程的ThreadLocal内
 * 对于每次遍历,执行一个内置的Processor的处理.
 * 此内置的Processor如果需要访问当前遍历实体,可以从IterableItemStackHolder内获取;
 * 如果需要对CrawlContext进行循环读写,那么需要选用一个合适的CrawlContextAccessor
 * 
 * 此处理器还支持并发处理,注入一个线程池并将parallel设置为true,可以并行的进行遍历
 * 
 * 注意,遍历过程中如果需要访问遍历实体,那么必须在执行内置Processor的线程内进行,如果另外开启线程,因为访问不到IterableItemStack
 * @author rec
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractIterableProcessor implements Action {
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractIterableProcessor.class);

	protected abstract Object getItems(ActionContext crawlContext);

	protected Action nestedProcessor;
	protected boolean omitAbsence;
	protected boolean parallel;
	protected boolean omitException;
	protected ExecutorService threadPool;

	@Override
	public void execute(ActionContext crawlContext) throws Exception {
		Object object = getItems(crawlContext);
		if (object == null) {
			if (omitAbsence)
				return;
			else
				throw new IllegalArgumentException(
						"cannot get iterable items of crawlURL "
								+ crawlContext.getCrawlURL());
		}

		List<IterableItem> list = null;

		Class clazz = object.getClass();

		if (clazz.isAssignableFrom(Map.class)) {
			list = traverseMap(object, crawlContext);
		} else if (clazz.isAssignableFrom(Collection.class)) {
			list = traverseCollection(object, crawlContext);
		} else if (clazz.isArray()) {
			list = traverseArray(object, crawlContext);
		} else {
			throw new IllegalArgumentException(
					"items are not iterable , class " + clazz + " crawlURL "
							+ crawlContext.getCrawlURL());
		}

		if (parallel) {
			Assert.notNull(threadPool, "property threadPool is not configed but parallel is true");

			List<IterableProcessCallable> tasks = new ArrayList<IterableProcessCallable>(
					list.size());
			for (IterableItem item : list) {
				tasks.add(new IterableProcessCallable(nestedProcessor,
						crawlContext, item));
			}
			List<Future<Void>> reaults = threadPool.invokeAll(tasks);
			for (int i = 0; i < reaults.size(); i++) {
				try {
					reaults.get(i);
				} catch (Exception e) {
					if (omitException) {
						logger.warn(
								"error occured and omitted when process crawlURL: "
										+ crawlContext.getCrawlURL()
										+ " and processor index is " + i
										+ " and processor is "
										+ nestedProcessor, e);
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
					nestedProcessor.execute(crawlContext);
				} catch (Exception e) {
					if (omitException) {
						logger.warn(
								"error occured and omitted when process crawlURL: "
										+ crawlContext.getCrawlURL()
										+ " and processor index is " + i
										+ " and processor is "
										+ nestedProcessor, e);
						continue;
					} else {
						throw e;
					}
				} finally {
					IterableItemStackHolder.popItem();
				}
			}

			IterableItemStackHolder.popItem();
		}
	}

	protected List<IterableItem> traverseMap(Object object,
			ActionContext crawlContext) throws Exception {
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
			ActionContext crawlContext) throws Exception {
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
			ActionContext crawlContext) throws Exception {
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

	public void setOmitAbsence(boolean omitAbsence) {
		this.omitAbsence = omitAbsence;
	}

	public void setNestedProcessor(Action nestedProcessor) {
		this.nestedProcessor = nestedProcessor;
	}

	public void setParallel(boolean parallel) {
		this.parallel = parallel;
	}

	public void setOmitException(boolean omitException) {
		this.omitException = omitException;
	}

	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}
}
