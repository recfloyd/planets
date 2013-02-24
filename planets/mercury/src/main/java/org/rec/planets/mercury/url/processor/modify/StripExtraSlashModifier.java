package org.rec.planets.mercury.url.processor.modify;

import jregex.Matcher;
import jregex.Pattern;
import jregex.REFlags;

/**
 * 去除url中多余的/,即//变为/
 * 
 * @author rec
 * 
 */
public final class StripExtraSlashModifier extends AbstractModifier {
	private StripExtraSlashModifier() {
	}

	private static volatile StripExtraSlashModifier instance;

	public static StripExtraSlashModifier getInstance() {
		if (instance == null) {
			synchronized (StripExtraSlashModifier.class) {
				if (instance == null)
					instance = new StripExtraSlashModifier();
			}
		}
		return instance;
	}

	private static final Pattern REGEX = new Pattern("(^https?://.*?)//+(.*)",
			REFlags.IGNORE_CASE);

	@Override
	public String modify(String url, String baseURL) {
		Matcher matcher = REGEX.matcher(url);
		while (matcher.matches()) {
			url = matcher.group(1) + "/" + matcher.group(2);
			matcher = REGEX.matcher(url);
		}
		return url;
	}

}
