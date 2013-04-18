package org.rec.planets.jupiter.action.transfer.html;

import org.rec.planets.jupiter.action.AbstractReadProcessWriteAction;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.mercury.parse.RegexUtil;
import org.springframework.web.util.HtmlUtils;

/**
 * 去除html标签的转换器<br/>
 * <code>
 * 	<bean id="转换Action" class="">
 * 		<property name="contextReader" ref="源读取器"/>
 * 		<property name="sourceKey" value="源读取键"/>
 * 		<property name="omitSourceNull" value="false"/>
 * 		<property name="contextWriter" ref="目标写入器"/>
 * 		<property name="resultKey" value="目标写入键"/>
 * 		<property name="omitSourceNull" value="false"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class HTMLEscapeTransferer extends AbstractReadProcessWriteAction {

	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		String html = (String) source;

		// 去除script
		html = RegexUtil.replaceAll(html,
				"<\\s*script.*?>.*?<\\s*/script\\s*>", "");
		// 去掉注释
		html = RegexUtil.replaceAll(html, "<!--.*?-->", "");

		// 多个空格转换成一个空格
		html = RegexUtil.replaceAll(html, "[\\r\\n]+", " ");
		html = RegexUtil.replaceAll(html, "&(nbsp|#160);", " ");
		html = RegexUtil.replaceAll(html, "\\s+", " ");

		// HTML标签
		html = RegexUtil.replaceAll(html, "</?.*?>", "");
		html = HtmlUtils.htmlUnescape(html);

		return html;
	}

}
