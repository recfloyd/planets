package org.rec.planets.mercury.url.processor.modify;

import org.rec.planets.mercury.parse.URLUtil;

/**
 * 转换为绝对路径
 * 
 * @author rec
 * 
 */
public final class AbsolutePathModifier extends AbstractModifier {
	private AbsolutePathModifier() {
	}

	private static volatile AbsolutePathModifier instance;

	public static AbsolutePathModifier getInstance() {
		if (instance == null) {
			synchronized (AbsolutePathModifier.class) {
				if (instance == null)
					instance = new AbsolutePathModifier();
			}
		}
		return instance;
	}

	@Override
	public String modify(String url, String baseURL) {
		return URLUtil.getAbsolutePath(url, baseURL);
	}

}
