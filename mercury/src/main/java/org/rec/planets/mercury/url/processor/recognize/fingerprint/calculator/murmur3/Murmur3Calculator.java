package org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.murmur3;

import org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.Calculator;

public class Murmur3Calculator implements Calculator {
	private static final int SEED = 31;

	@Override
	public long calculate(String string) throws Exception {
		byte[] bytes = string.getBytes();
		return MurmurHash3Function.MurmurHash3_x64_64(bytes, SEED);
	}
}
