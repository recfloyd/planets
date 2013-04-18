package org.rec.planets.jupiter.action.parser.regex;

import org.rec.planets.mercury.parse.RegexUtil;

/**
 * 首次匹配正则解析器,它匹配的结果是一个List<String>,list里面的内容是各个group的值<br/>
 * <code>
 * 	<bean id="正则解析Action" class="">
 * 		<property name="contextReader" ref="文本读取器"/>
 * 		<property name="sourceKey" ref="文本读取键"/>
 * 		<property name="omitSourceNull" ref="false"/>
 * 		<property name="contextWriter" ref="结果写入器"/>
 * 		<property name="resultKey" ref="结果写入键"/>
 * 		<property name="omitSourceNull" ref="true"/>
 * 		<property name="regex" ref="..."/>
 * 	</bean>
 * </code>
 * @author rec
 *
 */
public class FirstMatchStringListRegexParser extends AbstractRegexParser {
	@Override
	protected Object find(String text) {
		return RegexUtil.getFirstGroups(text, regex);
	}
}