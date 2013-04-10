package org.rec.planets.jupiter.action.network.stat;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.interceptor.Interceptor;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;
import org.rec.planets.jupiter.slot.snapshot.JobResultSnapshotFactory;

/**
 * 下载异常拦截器,该拦截器适合紧紧包装住下载器<br/>
 * <code>
 * 	<bean id="下载异常Interceptor" class="">
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class DownloadExceptionInterceptor implements Interceptor {

	@Override
	public void invoke(Action action, ActionContext context) throws Exception {
		try {
			action.execute(context);
		} catch (Exception e) {
			JobResultSnapshotFactory jobResultSnapshotFactory = (JobResultSnapshotFactory) context
					.getUrlcontext()
					.get(ActionContextConstants.KEY_JOB_RESULT_SNAPSHORT_FACTORY);
			if (jobResultSnapshotFactory == null) {
				throw new Exception(
						"cannot found jobResultSnapshotFactory from key "
								+ ActionContextConstants.KEY_JOB_RESULT_SNAPSHORT_FACTORY
								+ " of crawlURL " + context.getCrawlURL(), e);
			} else {
				jobResultSnapshotFactory.addDownloadFailed(context
						.getCrawlURL().getId());
			}

			throw e;
		}
	}

}
