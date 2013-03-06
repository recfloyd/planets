package org.rec.planets.jupiter.action.network.stat;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;
import org.rec.planets.jupiter.context.accessor.ContextWriter;
import org.rec.planets.jupiter.slot.snapshot.JobResultSnapshotFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.google.common.base.Strings;

public class StatusCodeCheckAction extends AbstractResponseReadable implements
		Action {
	private static final Logger logger = LoggerFactory
			.getLogger(StatusCodeCheckAction.class);

	private ContextWriter contextWriter;
	private String notModifiedKey;
	private boolean omitException;

	@Override
	public void execute(ActionContext context) throws Exception {
		Response<?> response = getResponse(context);

		if (response == null)
			return;

		JobResultSnapshotFactory jobResultSnapshotFactory = (JobResultSnapshotFactory) context
				.getUrlcontext()
				.get(ActionContextConstants.KEY_JOB_RESULT_SNAPSHORT_FACTORY);
		if (jobResultSnapshotFactory == null) {
			logger.warn("cannot found jobResultSnapshotFactory from key "
					+ ActionContextConstants.KEY_JOB_RESULT_SNAPSHORT_FACTORY
					+ " of crawlURL " + context.getCrawlURL());
			return;
		}

		HttpStatus status = HttpStatus.valueOf(response.getStatusCode());

		if (status == HttpStatus.NOT_MODIFIED) {
			jobResultSnapshotFactory.addUnmodified(context.getCrawlURL()
					.getId());
			if (!Strings.isNullOrEmpty(notModifiedKey)) {
				contextWriter.write(context, notModifiedKey, true);
			}
			return;
		} else if (status.series() != HttpStatus.Series.SUCCESSFUL) {
			jobResultSnapshotFactory.addDownloadFailed(context.getCrawlURL()
					.getId());
			if (!omitException)
				throw new Exception("error code of " + status
						+ " when downloading crawlURL " + context.getCrawlURL());
		}
	}

}
