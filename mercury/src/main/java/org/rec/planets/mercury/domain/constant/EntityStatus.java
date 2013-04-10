package org.rec.planets.mercury.domain.constant;

public enum EntityStatus {
	Available((byte) 1), Unavailable((byte) 0);

	private final byte value;

	private EntityStatus(byte value) {
		this.value = value;
	}

	public EntityStatus valueOf(byte value) {
		for (EntityStatus es : EntityStatus.values()) {
			if (es.value == value)
				return es;
		}
		return null;
	}
}
