package org.rec.planets.mercury.domain;

import com.google.common.base.Objects;

public abstract class AbstractBean {
	@Override
	public String toString() {
		return Objects.toStringHelper(this).toString();
	}
}
