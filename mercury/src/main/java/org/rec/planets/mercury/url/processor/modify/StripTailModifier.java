package org.rec.planets.mercury.url.processor.modify;

/**
 * 去除最后一个/以及其后的部分
 * 
 * @author rec
 * 
 */
public final class StripTailModifier extends AbstractStripModifier {
	private static final long serialVersionUID = 5418713115584472031L;

	private StripTailModifier() {
	}

	private static volatile StripTailModifier instance;

	public static StripTailModifier getInstance() {
		if (instance == null) {
			synchronized (StripTailModifier.class) {
				if (instance == null)
					instance = new StripTailModifier();
			}
		}
		return instance;
	}

	@Override
	protected String modify(String url, String baseURL) {
		int index = url.lastIndexOf('/');
		if (index > 0) {
			url = url.substring(0, index);
		}
		return url;
	}

}
