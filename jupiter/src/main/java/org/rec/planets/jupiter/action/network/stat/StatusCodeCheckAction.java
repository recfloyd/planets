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

/**
 * 响应码检查器,该检查器适合紧跟下载器<br/>
 * <code>
 * 	<bean id="响应码检查Action" class="">
 * 		<property name="contextReader" ref="响应读取器"/>
 * 		<property name="resultKey" value="响应读取键"/>
 * 		<property name="omitNull" value="..."/>
 * 		<property name="omitException" value="..."/><!--如果响应码不是200,是否抛出异常-->
 * 		<!--以下2项可以省略,当返回304的时候,可以向context写入一个boolean值,方便后续判断-->
 * 		<property name="contextWriter" ref="304写入器"/>
 * 		<property name="notModifiedKey" value="304写入键"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
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

		HttpStatus status = response.getHttpResponse().getStatusCode();

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

	public void setContextWriter(ContextWriter contextWriter) {
		this.contextWriter = contextWriter;
	}

	public void setNotModifiedKey(String notModifiedKey) {
		this.notModifiedKey = notModifiedKey;
	}

	public void setOmitException(boolean omitException) {
		this.omitException = omitException;
	}
}
