package org.rec.planets.mercury.url.processor.modify;

import java.util.List;

import org.rec.planets.mercury.parse.RegexUtil;
import org.rec.planets.mercury.parse.bean.Regex;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Joiner;

/**
 * @author rec
 * 
 */
public abstract class AbstractStripModifier extends AbstractModifier {

	protected String extractFromRegex(String url, Regex regex) {
		List<String> s = RegexUtil.groupFirstMatch(url, regex, true);
		if (CollectionUtils.isEmpty(s))
			return url;
		else
			return Joiner.on("").skipNulls().join(s);
	}

}