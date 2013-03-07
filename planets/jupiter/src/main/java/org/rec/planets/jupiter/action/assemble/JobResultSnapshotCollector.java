package org.rec.planets.jupiter.action.assemble;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;
import org.rec.planets.jupiter.context.accessor.AbstractReadSupport;
import org.rec.planets.jupiter.slot.snapshot.JobResultSnapshotFactory;
import org.rec.planets.mercury.communication.bean.CrawlEntity;
import org.rec.planets.mercury.communication.bean.CrawlPropagation;

/**
 * 把CrawlEntity和CrawlPropagation提取出来放到快照里面,一般放在抽取正式完成后进行
 * 
 * @author rec
 * 
 */
public class JobResultSnapshotCollector extends AbstractReadSupport implements
		Action {
	protected JobResultSnapshotFactory getJobResultSnapshotFactory(
			ActionContext context) throws Exception {
		JobResultSnapshotFactory jobResultSnapshotFactory = (JobResultSnapshotFactory) context
				.getUrlcontext()
				.get(ActionContextConstants.KEY_JOB_RESULT_SNAPSHORT_FACTORY);
		if (jobResultSnapshotFactory == null) {
			throw new Exception(
					"cannot found jobResultSnapshotFactory from key "
							+ ActionContextConstants.KEY_JOB_RESULT_SNAPSHORT_FACTORY
							+ " of crawlURL " + context.getCrawlURL());
		}
		return jobResultSnapshotFactory;
	}

	@Override
	public void execute(ActionContext context) throws Exception {
		JobResultSnapshotFactory jobResultSnapshotFactory = getJobResultSnapshotFactory(context);

		Object source = getSource(context);

		if (source != null) {
			if (source instanceof CrawlEntity) {
				jobResultSnapshotFactory.addEntity((CrawlEntity) source);
			} else if (source instanceof CrawlPropagation) {
				jobResultSnapshotFactory
						.addPropagation((CrawlPropagation) source);
			} else {
				throw new Exception(
						"source object from key "
								+ sourceKey
								+ " is not CrawlEntity nor CrawlPropagation of crawlURL "
								+ context.getCrawlURL());
			}
		}
	}
}
