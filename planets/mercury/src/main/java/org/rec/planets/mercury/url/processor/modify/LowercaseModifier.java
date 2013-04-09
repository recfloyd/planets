package org.rec.planets.mercury.url.processor.modify;

/**
 * url转化为小写
 * 
 * @author rec
 * 
 */
public final class LowercaseModifier extends AbstractModifier {
	private static final long serialVersionUID = 2931118026633979167L;

	private LowercaseModifier() {
	}

	private static volatile LowercaseModifier instance;

	public static LowercaseModifier getInstance() {
		if (instance == null) {
			synchronized (LowercaseModifier.class) {
				if (instance == null)
					instance = new LowercaseModifier();
			}
		}
		return instance;
	}

	@Override
	public String modify(String url, String baseURL) {
		return url.toLowerCase();
	}

}
