package org.rec.planets.jupiter.action.parser.regex;

import org.rec.planets.mercury.parse.RegexUtil;

/**
 * 首次匹配正则解析器,它匹配的结果是一个String,只获取一个group<br/>
 * <code>
 * 	<bean id="正则解析Action" class="">
 * 		<property name="contextReader" ref="文本读取器"/>
 * 		<property name="sourceKey" value="文本读取键"/>
 * 		<property name="omitSourceNull" value="false"/>
 * 		<property name="contextWriter" ref="结果写入器"/>
 * 		<property name="resultKey" value="结果写入键"/>
 * 		<property name="omitResultNull" value="true"/>
 * 		<property name="regex" ref="..."/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class SingleMatchStringRegexParser extends AbstractRegexParser {

	@Override
	protected Object find(String text) {
		return RegexUtil.getSingleMatch(text, regex);
	}
}
