package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.bean.Regex;

/**
 * 去掉域名中的www
 * 
 * @author rec
 * 
 */
public final class StripWWWModifier extends AbstractStripModifier {
	private StripWWWModifier() {
	}

	private static volatile StripWWWModifier instance;

	public static StripWWWModifier getInstance() {
		if (instance == null) {
			synchronized (StripWWWModifier.class) {
				if (instance == null)
					instance = new StripWWWModifier();
			}
		}
		return instance;
	}

	private static final Regex BASE_PATTERN = new Regex(
			"^(https?://)(?:www\\.)([^/]*/.+)$", new int[] { 1, 2 });

	@Override
	public String modify(String url, String baseURL) {
		return super.extractFromRegex(url, BASE_PATTERN);
	}

}
