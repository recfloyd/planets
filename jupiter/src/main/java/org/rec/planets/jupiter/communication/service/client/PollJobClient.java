package org.rec.planets.jupiter.communication.service.client;

import java.util.Map;

import org.rec.planets.jupiter.slot.Slot;
import org.rec.planets.jupiter.slot.SlotFactory;
import org.rec.planets.jupiter.system.command.CommandHandler;
import org.rec.planets.jupiter.system.node.NodeIdHolder;
import org.rec.planets.mercury.communication.bean.pack.JobPack;
import org.rec.planets.mercury.communication.bean.pack.PollPack;
import org.rec.planets.mercury.communication.service.PollJobService;
import org.rec.planets.mercury.domain.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PollJobClient {
	private static final Logger logger = LoggerFactory
			.getLogger(PollJobClient.class);
	private PollJobService pollJobService;
	private SlotFactory slotFactory;
	private CommandHandler commandHandler;

	public void execute() throws Exception {
		PollPack pollPack = new PollPack();
		pollPack.setNodeId(NodeIdHolder.getNodeId());
		pollPack.setRuleVersions(slotFactory.getRuleVersions());

		JobPack jobPack = pollJobService.poll(pollPack);

		if (jobPack == null)
			return;

		Map<String, Object> command = jobPack.getCommand();
		if (command != null && command.size() > 0) {
			if (commandHandler == null) {
				throw new RuntimeException("there's no command hanlder for command "
						+ command);
			}
			commandHandler.handle(command);
		}

		Slot slot = null;
		for (Job job : jobPack.getJobs()) {
			slot = slotFactory.getSlot(job.getWebsiteId(), null);
			if (slot == null) {
				logger.error("no slot config for websiteId "
						+ job.getWebsiteId());
				continue;
			}
			slot.submitJob(job);
		}
	}

	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}

	public void setPollJobService(PollJobService pollJobService) {
		this.pollJobService = pollJobService;
	}

	public void setSlotFactory(SlotFactory slotFactory) {
		this.slotFactory = slotFactory;
	}
}
