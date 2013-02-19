package org.rec.planets.mercury.domain.constant;

public enum BandwidthType {
	Sharing((byte) 1), Excluded((byte) 2);

	private final byte value;

	private BandwidthType(byte value) {
		this.value = value;
	}

	public BandwidthType valueOf(byte value) {
		for (BandwidthType bt : BandwidthType.values()) {
			if (bt.value == value)
				return bt;
		}
		return null;
	}
}
