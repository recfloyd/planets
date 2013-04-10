package org.rec.planets.jupiter.action.network.stat;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.interceptor.Interceptor;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;
import org.rec.planets.jupiter.slot.snapshot.WebsiteSnapshotFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * 带宽统计拦截器,该拦截器适合紧紧包装住下载器<br/>
 * <code>
 * 	<bean id="带宽拦截Interceptor" class="">
 * 		<property name="contextReader" ref="响应读取器"/>
 * 		<property name="resultKey" ref="响应读取键"/>
 * 		<property name="omitNull" ref="..."/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class BandwidthStatInterceptor extends AbstractResponseReadable
		implements Interceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(BandwidthStatInterceptor.class);

	@Override
	public void invoke(Action action, ActionContext context) throws Exception {
		long time = System.currentTimeMillis();
		action.execute(context);
		time = System.currentTimeMillis() - time;

		Response<?> response = getResponse(context);
		if (response == null)
			return;

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

		ResponseEntity<?> responseEntity = response.getHttpResponse();
		long contentLength = -1;
		if (responseEntity.hasBody()) {
			HttpHeaders httpHeaders = responseEntity.getHeaders();
			if (httpHeaders != null) {
				contentLength = httpHeaders.getContentLength();
			}
			if (contentLength < 0) {// 如果从头信息中拿不到contentLength,那么尝试从body中近似获取
				Object body = responseEntity.getBody();
				if (body instanceof String) {
					contentLength = ((String) body).getBytes().length;
				} else if (body instanceof byte[]) {
					contentLength = ((byte[]) body).length;
				} else {
					contentLength = 0;
				}
			}
		} else {
			contentLength = 0;
		}
		websiteSnapshotFactory.record(contentLength, time);
	}
}
