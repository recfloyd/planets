package org.rec.planets.jupiter.action.parser.regex;

import java.util.List;

import org.rec.planets.mercury.parse.RegexUtil;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Joiner;

/**
 * 首次匹配正则解析器,它匹配的结果是一个String,是由joiner字符串连接的各group的值
 * 
 * @author rec
 * 
 */
public class FirstMatchStringRegexParser extends AbstractRegexParser {
	protected String joiner = "";

	@Override
	protected Object find(String text) {
		List<String> result = RegexUtil.getFirstGroups(text, regex);
		if (!CollectionUtils.isEmpty(result))
			return Joiner.on(joiner).join(result);
		else
			return null;
	}

	public void setJoiner(String joiner) {
		this.joiner = joiner;
	}
}
