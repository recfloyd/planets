package org.rec.planets.jupiter.action.parser.regex;

import org.rec.planets.mercury.parse.RegexUtil;

/**
 * 首次匹配正则解析器,它匹配的结果是一个List<String>,list里面的内容是各个group的值
 * @author rec
 *
 */
public class FirstMatchStringListRegexParser extends AbstractRegexParser {
	@Override
	protected Object find(String text) {
		return RegexUtil.getFirstGroups(text, regex);
	}
}
