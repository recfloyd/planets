package org.rec.planets.mercury.domain.constant;

public enum CrawlStatus {
	Normal((byte) 1), NotFound((byte) 2), Failure((byte) 3);

	private final byte value;

	private CrawlStatus(byte value) {
		this.value = value;
	}

	public CrawlStatus valueOf(byte value) {
		for (CrawlStatus cs : CrawlStatus.values()) {
			if (cs.value == value)
				return cs;
		}
		return null;
	}
}
