package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.URLUtil;

/**
 * 用于去除url中的锚点
 * 
 * @author rec
 * 
 */
public final class StripRefModifier extends AbstractModifier {
	private StripRefModifier() {
	}

	private static volatile StripRefModifier instance;

	public static StripRefModifier getInstance() {
		if (instance == null) {
			synchronized (StripRefModifier.class) {
				if (instance == null)
					instance = new StripRefModifier();
			}
		}
		return instance;
	}

	@Override
	public String modify(String url, String baseURL) {
		return URLUtil.stripRef(url);
	}

}
