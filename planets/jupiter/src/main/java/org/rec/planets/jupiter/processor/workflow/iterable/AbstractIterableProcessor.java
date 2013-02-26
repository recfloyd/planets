package org.rec.planets.jupiter.processor.workflow.iterable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.CrawlProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractIterableProcessor implements CrawlProcessor {
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractIterableProcessor.class);

	protected abstract Object getItems(CrawlContext crawlContext);

	protected CrawlProcessor nestedProcessor;
	protected boolean omitAbsence;
	protected boolean parallel;
	protected boolean omitException;
	protected ExecutorService threadPool;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
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
			if (threadPool == null)
				throw new IllegalArgumentException(
						"property threadPool is not configed but parallel is true");

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
					nestedProcessor.process(crawlContext);
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
			CrawlContext crawlContext) throws Exception {
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
			CrawlContext crawlContext) throws Exception {
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
			CrawlContext crawlContext) throws Exception {
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

	public void setNestedProcessor(CrawlProcessor nestedProcessor) {
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
