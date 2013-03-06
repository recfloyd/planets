package org.rec.planets.jupiter.action.network.stat;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.interceptor.Interceptor;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;
import org.rec.planets.jupiter.context.accessor.ContextReader;
import org.rec.planets.jupiter.slot.snapshot.WebsiteSnapshotFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 带宽统计拦截器,该拦截器适合紧紧包装住下载器
 * 
 * @author rec
 * 
 */
public class BandwidthStatInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(BandwidthStatInterceptor.class);
	private String resultKey;
	private ContextReader contextReader;

	@Override
	public void invoke(Action action, ActionContext context) throws Exception {
		WebsiteSnapshotFactory websiteSnapshotFactory = (WebsiteSnapshotFactory) context
				.getUrlcontext().get(
						ActionContextConstants.KEY_WEBSITE_SNAPSHOT_FACTORY);
		if (websiteSnapshotFactory == null) {
			logger.warn("cannot found websiteSnapshotFactory from key "
					+ ActionContextConstants.KEY_WEBSITE_SNAPSHOT_FACTORY
					+ " of crawlURL " + context.getCrawlURL());
			action.execute(context);
			return;
		}

		long time = System.currentTimeMillis();
		action.execute(context);
		time = System.currentTimeMillis() - time;

		Response<?> response = (Response<?>) contextReader.read(context,
				resultKey);
		int statusCode = response.getStatusCode();
		if (statusCode / 100 == 2) {
			websiteSnapshotFactory.record(response.getContentLength(), time);
		}
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

}
