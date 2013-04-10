package org.rec.planets.mercury.url.processor.recognize.fingerprint.calculator.elf;

public class ELFHashFunction {
	public long hash(String str) {
		long hash = 0;
		long x = 0;
		for (int i = 0; i < str.length(); i++) {
			hash = (hash << 4) + str.charAt(i);
			if ((x = hash & 0xF0000000L) != 0) {
				hash ^= (x >> 24);
			}
			hash &= ~x;
		}
		return hash;
	}
}
