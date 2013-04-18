package org.rec.planets.jupiter.communication.service.client;

import java.util.Map;

import org.rec.planets.jupiter.slot.SlotFactory;
import org.rec.planets.jupiter.system.command.CommandHandler;
import org.rec.planets.jupiter.system.node.NodeIdHolder;
import org.rec.planets.mercury.communication.bean.pack.ResultPack;
import org.rec.planets.mercury.communication.service.PushResultService;

public class PushResultClient {
	private PushResultService pushResultService;
	private SlotFactory slotFactory;
	private CommandHandler commandHandler;

	public void execute() throws Exception {
		ResultPack resultPack = new ResultPack();
		resultPack.setNodeId(NodeIdHolder.getNodeId());
		resultPack.setJobResultSnapshots(slotFactory.getJobResultSnapshots());
		Map<String, Object> command = pushResultService.push(resultPack);

		if (command != null && command.size() > 0) {
			if (commandHandler == null) {
				throw new RuntimeException("there's no command hanlder for command "
						+ command);
			}
			commandHandler.handle(command);
		}
	}

	public void setPushResultService(PushResultService pushResultService) {
		this.pushResultService = pushResultService;
	}

	public void setSlotFactory(SlotFactory slotFactory) {
		this.slotFactory = slotFactory;
	}

	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}
}
