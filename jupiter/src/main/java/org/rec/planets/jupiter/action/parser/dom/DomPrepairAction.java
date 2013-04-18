package org.rec.planets.jupiter.action.parser.dom;

import org.jsoup.Jsoup;
import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.context.ActionContext;

import com.google.common.base.Strings;

/**
 * DOM准备器<br/>
 * <code>
 * 	<bean id="dom准备Action" class="">
 * 		<property name="contextReader" ref="响应读取器"/>
 * 		<property name="sourceKey" value="响应读取键"/>
 * 		<property name="omitSourceNull" value="true"/>
 * 		<property name="contextWriter" ref="DOM写入器"/>
 * 		<property name="resultKey" value="DOM写入键"/>
 * 		<property name="omitSourceNull" value="false"/>
 * 	</bean>
 * </code>
 * @author rec
 *
 */
public class DomPrepairAction extends AbstractReadProcessWriteAction {

	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		@SuppressWarnings("unchecked")
		Response<String> response = (Response<String>) source;
		if (response != null) {
			String content = response.getHttpResponse().getBody();
			if (Strings.isNullOrEmpty(content))
				return null;
			else {
				return Jsoup.parse(content);
			}
		}
		return null;
	}

}
