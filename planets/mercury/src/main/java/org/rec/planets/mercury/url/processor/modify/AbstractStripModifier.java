package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.RegexUtil;
import org.rec.planets.mercury.parse.bean.Regex;

import com.google.common.base.Joiner;

/**
 * @author rec
 * 
 */
public abstract class AbstractStripModifier extends AbstractModifier {

	protected String extractFromRegex(String url, Regex regex) {
		String[] s = RegexUtil.groupFirstMatch(url, regex, true);
		if (s == null)
			return url;
		else
			return Joiner.on("").skipNulls().join(s);
	}

}