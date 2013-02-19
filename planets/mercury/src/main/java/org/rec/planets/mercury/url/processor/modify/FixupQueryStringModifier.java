package org.rec.planets.mercury.url.processor.modify;

/**
 * 对参数串进行符号除错,去除多余的?和&
 * 
 * @author rec
 * 
 */
public class FixupQueryStringModifier extends AbstractModifier {
	private FixupQueryStringModifier() {
	}

	private static volatile FixupQueryStringModifier instance;

	public static FixupQueryStringModifier getInstance() {
		if (instance == null) {
			synchronized (FixupQueryStringModifier.class) {
				if (instance == null)
					instance = new FixupQueryStringModifier();
			}
		}
		return instance;
	}

	@Override
	public String modify(String url, String baseURL) {
		int index = url.lastIndexOf('?');
		if (index > 0) {
			if (index == (url.length() - 1)) {
				// '?' is last char in url. Strip it.
				url = url.substring(0, url.length() - 1);
			} else if (url.charAt(index + 1) == '&') {
				// Next char is '&'. Strip it.
				if (url.length() == (index + 2)) {
					// Then url ends with '?&'. Strip them.
					url = url.substring(0, url.length() - 2);
				} else {
					// The '&' is redundant. Strip it.
					url = url.substring(0, index + 1)
							+ url.substring(index + 2);
				}
			} else if (url.charAt(url.length() - 1) == '&') {
				// If we have a lone '&' on end of query str,
				// strip it.
				url = url.substring(0, url.length() - 1);
			}
		}
		return url;
	}

}
