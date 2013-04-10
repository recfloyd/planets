package org.rec.planets.jupiter.slot;

import java.util.List;
import java.util.Map;

import org.rec.planets.mercury.communication.bean.snapshot.CurrentJobSnapshot;
import org.rec.planets.mercury.communication.bean.snapshot.JobResultSnapshot;
import org.rec.planets.mercury.communication.bean.snapshot.WebsiteSnapshot;

public interface SlotFactory {
	Slot getSlot(Short websiteId, String[] resources) throws Exception;

	void updateRule(Short websiteId, String[] resources) throws Exception;

	Map<Short, Long> getRuleVersions();

	List<JobResultSnapshot> getJobResultSnapshots();

	List<CurrentJobSnapshot> getCurrentJobSnapshots();

	List<WebsiteSnapshot> getWebsiteSnapshots();
}
