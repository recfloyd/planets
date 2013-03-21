package org.rec.planets.jupiter.communication.service.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.rec.planets.jupiter.slot.Slot;
import org.rec.planets.jupiter.system.CommandHandler;
import org.rec.planets.jupiter.system.NodeIdHolder;
import org.rec.planets.mercury.communication.bean.pack.ResultPack;
import org.rec.planets.mercury.communication.bean.snapshot.JobResultSnapshot;
import org.rec.planets.mercury.communication.service.PushResultService;

public class PushResultClient {
	private PushResultService pushResultService;
	private List<Slot> slots;
	private CommandHandler commandHandler;

	public void execute() throws Exception {
		ResultPack resultPack = new ResultPack();
		resultPack.setNodeId(NodeIdHolder.getNodeId());
		resultPack.setJobResultSnapshots(new ArrayList<JobResultSnapshot>(slots
				.size() * 2));
		for (Slot slot : slots) {
			resultPack.getJobResultSnapshots().addAll(
					slot.getJobResultSnapshots());
		}
		Map<String, Object> command = pushResultService.push(resultPack);
		commandHandler.handle(command);
	}

	public void setPushResultService(PushResultService pushResultService) {
		this.pushResultService = pushResultService;
	}

	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}

	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}
}
