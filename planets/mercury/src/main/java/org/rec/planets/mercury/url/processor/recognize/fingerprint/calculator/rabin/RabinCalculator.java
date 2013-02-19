package org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.rabin;

import org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.Calculator;

public class RabinCalculator implements Calculator {
	private static final RabinHashFunction FUNCTION = new RabinHashFunction();

	@Override
	public long calculate(String string) throws Exception {
		return FUNCTION.hash(string);
	}
}
