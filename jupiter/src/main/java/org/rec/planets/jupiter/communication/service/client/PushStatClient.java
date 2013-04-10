package org.rec.planets.jupiter.communication.service.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.rec.planets.jupiter.slot.Slot;
import org.rec.planets.jupiter.system.CommandHandler;
import org.rec.planets.jupiter.system.NodeIdHolder;
import org.rec.planets.mercury.communication.bean.pack.StatPack;
import org.rec.planets.mercury.communication.bean.snapshot.CurrentJobSnapshot;
import org.rec.planets.mercury.communication.bean.snapshot.WebsiteSnapshot;
import org.rec.planets.mercury.communication.service.PushStatService;

public class PushStatClient {
	private List<Slot> slots;
	private PushStatService pushStatService;
	private CommandHandler commandHandler;

	public void execute() throws Exception {
		StatPack statPack = new StatPack();
		statPack.setNodeId(NodeIdHolder.getNodeId());
		statPack.setCurrentJobSnapshots(new ArrayList<CurrentJobSnapshot>(slots
				.size() * 2));
		statPack.setWebsiteSnapshots(new ArrayList<WebsiteSnapshot>(slots
				.size()));

		for (Slot slot : slots) {
			statPack.getCurrentJobSnapshots().addAll(
					slot.getCurrentJobSnapshot());
			statPack.getWebsiteSnapshots().add(slot.getWebsiteSnapshot());
		}

		Map<String, Object> command = pushStatService.push(statPack);
		commandHandler.handle(command);
	}

	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}

	public void setPushStatService(PushStatService pushStatService) {
		this.pushStatService = pushStatService;
	}

	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}
}
