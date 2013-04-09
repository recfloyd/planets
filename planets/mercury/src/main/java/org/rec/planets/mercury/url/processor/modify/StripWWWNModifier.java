package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.bean.Regex;

/**
 * 去掉域名中的www(N)
 * 
 * @author rec
 * 
 */
public final class StripWWWNModifier extends AbstractStripModifier {
	private static final long serialVersionUID = 7884032962365462096L;

	private StripWWWNModifier() {
	}

	private static volatile StripWWWNModifier instance;

	public static StripWWWNModifier getInstance() {
		if (instance == null) {
			synchronized (StripWWWNModifier.class) {
				if (instance == null)
					instance = new StripWWWNModifier();
			}
		}
		return instance;
	}

	private static final Regex BASE_PATTERN = new Regex(
			"^(https?://)(?:www[0-9]*\\.)([^/]*/.+)$", new int[] { 1, 2 });

	@Override
	public String modify(String url, String baseURL) {
		return super.extractFromRegex(url, BASE_PATTERN);
	}

}
