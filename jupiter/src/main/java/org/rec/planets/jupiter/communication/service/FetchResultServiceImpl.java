package org.rec.planets.jupiter.communication.service;

import java.util.Map;

import org.rec.planets.jupiter.slot.SlotFactory;
import org.rec.planets.jupiter.system.command.CommandHandler;
import org.rec.planets.jupiter.system.node.NodeIdHolder;
import org.rec.planets.mercury.communication.bean.pack.JobPack;
import org.rec.planets.mercury.communication.bean.pack.ResultPack;
import org.rec.planets.mercury.communication.service.FetchResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchResultServiceImpl implements FetchResultService {
	private static final Logger logger = LoggerFactory
			.getLogger(FetchResultServiceImpl.class);
	private SlotFactory slotFactory;
	private CommandHandler commandHandler;

	@Override
	public ResultPack fetch(JobPack jobPack) {
		Map<String, Object> command = jobPack.getCommand();
		if (command != null && command.size() > 0) {
			if (commandHandler == null) {
				throw new RuntimeException(
						"there's no command hanlder for command " + command);
			}
			try {
				commandHandler.handle(command);
			} catch (Exception e) {
				logger.error("error when handle command " + command, e);
				throw new RuntimeException(e);
			}
		}

		ResultPack result = new ResultPack();
		result.setNodeId(NodeIdHolder.getNodeId());
		result.setJobResultSnapshots(slotFactory.getJobResultSnapshots());
		return result;
	}

	public void setSlotFactory(SlotFactory slotFactory) {
		this.slotFactory = slotFactory;
	}

	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}
}
