package org.rec.planets.jupiter.communication.service;

import java.util.ArrayList;
import java.util.List;

import org.rec.planets.jupiter.slot.Slot;
import org.rec.planets.jupiter.system.NodeIdHolder;
import org.rec.planets.mercury.communication.bean.pack.StatPack;
import org.rec.planets.mercury.communication.bean.snapshot.CurrentJobSnapshot;
import org.rec.planets.mercury.communication.bean.snapshot.WebsiteSnapshot;
import org.rec.planets.mercury.communication.service.FetchStatService;

public class FetchStatServiceImpl implements FetchStatService {
	private List<Slot> slots;

	@Override
	public StatPack fetch() {
		StatPack result = new StatPack();
		result.setNodeId(NodeIdHolder.getNodeId());
		result.setCurrentJobSnapshots(new ArrayList<CurrentJobSnapshot>(slots
				.size() * 2));
		result.setWebsiteSnapshots(new ArrayList<WebsiteSnapshot>(slots.size()));

		for (Slot slot : slots) {
			result.getCurrentJobSnapshots()
					.addAll(slot.getCurrentJobSnapshot());
			result.getWebsiteSnapshots().add(slot.getWebsiteSnapshot());
		}
		return result;
	}

	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}

}
