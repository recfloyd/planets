package org.rec.planets.jupiter.slot;

import java.util.Map;

public interface SlotFactory {
	Slot getSlot(Short websiteId, String[] resources) throws Exception;

	void updateRule(Short websiteId, String[] resources) throws Exception;

	Map<Short, Long> getVersions();
}
