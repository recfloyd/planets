package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.URLUtil;

/**
 * 将参数全部删除
 * 
 * @author rec
 * 
 */
public class StripQueryModifier extends AbstractModifier {
	private StripQueryModifier() {
	}

	private static volatile StripQueryModifier instance;

	public static StripQueryModifier getInstance() {
		if (instance == null) {
			synchronized (StripQueryModifier.class) {
				if (instance == null)
					instance = new StripQueryModifier();
			}
		}
		return instance;
	}

	@Override
	protected String modify(String url, String baseURL) {
		return URLUtil.stripQuery(url);
	}

}
