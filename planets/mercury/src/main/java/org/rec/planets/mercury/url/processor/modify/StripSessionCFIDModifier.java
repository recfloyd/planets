package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.bean.Regex;

public final class StripSessionCFIDModifier extends AbstractStripModifier {
	private static final long serialVersionUID = 3564482478577515159L;

	private StripSessionCFIDModifier() {
	}

	private static volatile StripSessionCFIDModifier instance;

	public static StripSessionCFIDModifier getInstance() {
		if (instance == null) {
			synchronized (StripSessionCFIDModifier.class) {
				if (instance == null)
					instance = new StripSessionCFIDModifier();
			}
		}
		return instance;
	}

	private static final Regex BASE_PATTERN = new Regex(
			"^(.+)(?:cfid=[^&]+&cftoken=[^&]+(?:jsession=[^&]+)?)(?:&(.*))?$",
			new int[] { 1 });

	@Override
	public String modify(String url, String baseURL) {
		return super.extractFromRegex(url, BASE_PATTERN);
	}

}
