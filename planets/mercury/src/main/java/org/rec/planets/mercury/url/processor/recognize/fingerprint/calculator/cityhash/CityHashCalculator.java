package org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.cityhash;

import org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.Calculator;

public class CityHashCalculator implements Calculator {

	@Override
	public long calculate(String string) throws Exception {
		byte[] bytes = string.getBytes();
		return CityHashFunction.cityHash64(bytes, 0, bytes.length);
	}
}
