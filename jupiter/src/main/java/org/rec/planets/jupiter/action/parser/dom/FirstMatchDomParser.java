package org.rec.planets.jupiter.action.parser.dom;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 只取domElement第一个目标的dom解析器,匹配结果是一个String<br/>
 * <code>
 * 	<bean id="dom解析Action" class="">
 * 		<property name="contextReader" ref="DOM读取器"/>
 * 		<property name="sourceKey" value="DOM读取键"/>
 * 		<property name="omitSourceNull" value="false"/>
 * 		<property name="contextWriter" ref="结果写入器"/>
 * 		<property name="resultKey" value="结果写入键"/>
 * 		<property name="omitResultNull" value="true"/>
 * 		<property name="domElement" ref="..."/>
 * 		<property name="stringfy" value="true"/><!--是否解码为string,或者保持dom-->
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class FirstMatchDomParser extends AbstractDomParser {

	@Override
	protected Object extract(Elements elements, ActionContext context) {
		Element e = elements.first();
		return stringfy ? super.extractFromElement(e) : e;
	}

}
