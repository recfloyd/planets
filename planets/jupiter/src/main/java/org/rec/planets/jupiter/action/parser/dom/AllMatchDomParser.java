package org.rec.planets.jupiter.action.parser.dom;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rec.planets.jupiter.context.ActionContext;

import com.google.common.primitives.Ints;

/**
 * domElement规定的所有目标都抽取,匹配结果是一个List<String>
 * @author rec
 *
 */
public class AllMatchDomParser extends AbstractDomParser {

	@Override
	protected Object extract(Elements elements, ActionContext context) {
		List<String> result = new ArrayList<String>();
		Element e = null;
		int[] indexes = domElement.getIndexes();
		for (int i = 0; i < elements.size(); i++) {
			e = elements.get(i);
			if (indexes == null || Ints.contains(indexes, i)) {
				result.add(super.extractFromElement(e));
			}
		}
		return result;
	}

}
