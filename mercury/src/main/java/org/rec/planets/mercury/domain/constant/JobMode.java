package org.rec.planets.mercury.domain.constant;

public enum JobMode {
	Active((byte) 1), Passive((byte) 2);

	private final byte value;

	private JobMode(byte value) {
		this.value = value;
	}

	public JobMode valueOf(byte value) {
		for (JobMode jm : JobMode.values()) {
			if (jm.value == value)
				return jm;
		}
		return null;
	}
}
