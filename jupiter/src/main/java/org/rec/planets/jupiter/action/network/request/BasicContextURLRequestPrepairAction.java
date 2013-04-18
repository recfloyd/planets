package org.rec.planets.jupiter.action.network.request;

import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.http.HttpMethod;

import com.google.common.base.Strings;

/**
 * 基础请求创建器<br/>
 * <code>
 * 	<bean id="基础请求创建Action" class="">
 * 		<property name="contextReader" ref="url读取器"/>
 * 		<property name="sourceKey" ref="url读取键"/>
 * 		<property name="omitSourceNull" ref="true"/>
 * 		<property name="contextWriter" ref="请求写入器"/>
 * 		<property name="resultKey" ref="请求写入键"/>
 * 		<property name="omitSourceNull" ref="..."/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class BasicContextURLRequestPrepairAction extends
		AbstractReadProcessWriteAction {

	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		String url = (String) getSource(context);
		if (Strings.isNullOrEmpty(url))
			return null;
		else {
			Request request = new Request();
			request.setUrl(url);
			request.setMethod(HttpMethod.GET);

			return request;
		}
	}

}
