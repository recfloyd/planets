package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.URLUtil;

/**
 * 将参数全部删除
 * 
 * @author rec
 * 
 */
public final class StripQueryModifier extends AbstractModifier {
	private static final long serialVersionUID = -4211247485835158189L;

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
