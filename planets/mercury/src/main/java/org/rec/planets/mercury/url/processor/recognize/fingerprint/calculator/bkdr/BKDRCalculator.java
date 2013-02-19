package org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.bkdr;

import org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.Calculator;

public class BKDRCalculator implements Calculator {
	private static final BKDRHashFunction FUNCTION = new BKDRHashFunction();

	@Override
	public long calculate(String string) throws Exception {
		return FUNCTION.hash(string);
	}

}
