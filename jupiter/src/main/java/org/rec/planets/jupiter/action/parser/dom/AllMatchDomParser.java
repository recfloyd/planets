package org.rec.planets.jupiter.action.parser.dom;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rec.planets.jupiter.context.ActionContext;

import com.google.common.primitives.Ints;

/**
 * domElement规定的所有目标都抽取,匹配结果是一个List<String><br/>
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
public class AllMatchDomParser extends AbstractDomParser {

	@Override
	protected Object extract(Elements elements, ActionContext context) {
		List<Object> result = new ArrayList<Object>();
		Element e = null;
		int[] indexes = domElement.getIndexes();
		for (int i = 0; i < elements.size(); i++) {
			e = elements.get(i);
			if (indexes == null || Ints.contains(indexes, i)) {
				result.add(stringfy ? super.extractFromElement(e) : e);
			}
		}
		return result;
	}
}
