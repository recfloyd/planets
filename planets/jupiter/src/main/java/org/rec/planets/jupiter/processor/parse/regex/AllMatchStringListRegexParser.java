package org.rec.planets.jupiter.processor.parse.regex;

import org.rec.planets.mercury.parse.RegexUtil;

/**
 * 获取全部匹配内容的正则解析器,它解析的结果是一个List<List<String>>
 * @author rec
 *
 */
public class AllMatchStringListRegexParser extends AbstractRegexParser {

	@Override
	protected Object find(String text) {
		return RegexUtil.groupAllMatch(text, regex);
	}

}
