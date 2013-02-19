package org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.elf;

import org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.Calculator;

public class ELFCalculator implements Calculator {
	private static final ELFHashFunction FUNCTION = new ELFHashFunction();

	@Override
	public long calculate(String string) throws Exception {
		return FUNCTION.hash(string);
	}

}
