package org.rec.planets.mercury.domain.constant;

public enum CrawlType {
	Recycling((byte) 1), Disposable((byte) 2);

	private final byte value;

	private CrawlType(byte value) {
		this.value = value;
	}

	public CrawlType valueOf(byte value) {
		for (CrawlType ct : CrawlType.values()) {
			if (ct.value == value)
				return ct;
		}
		return null;
	}
}
