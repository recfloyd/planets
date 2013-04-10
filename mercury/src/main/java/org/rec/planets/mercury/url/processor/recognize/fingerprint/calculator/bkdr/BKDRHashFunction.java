package org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.bkdr;

public class BKDRHashFunction {
	private static final int seed = 131;

	public long hash(String str) {
		long hash = 0;
		int i = 0;
		for (i = 0; i < str.length(); i++) {
			hash = (hash * seed) + (str.charAt(i));
		}
		return hash;
	}
}
