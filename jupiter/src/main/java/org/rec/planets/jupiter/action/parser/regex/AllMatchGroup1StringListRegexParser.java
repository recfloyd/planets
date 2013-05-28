package org.rec.planets.jupiter.action.parser.regex;

import org.rec.planets.mercury.parse.RegexUtil;

/**
 * 获取全部匹配内容,并收集group 1的正则解析器,它解析的结果是一个List<String><br/>
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
public class AllMatchGroup1StringListRegexParser extends AbstractRegexParser {

	@Override
	protected Object find(String text) {
		return RegexUtil.getAllMatchGroup1(text, regex);
	}
}
