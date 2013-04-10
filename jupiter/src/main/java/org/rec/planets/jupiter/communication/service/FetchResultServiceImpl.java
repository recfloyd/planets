package org.rec.planets.jupiter.communication.service;

import java.util.ArrayList;
import java.util.Map;

import org.rec.planets.jupiter.slot.Slot;
import org.rec.planets.jupiter.system.CommandHandler;
import org.rec.planets.jupiter.system.NodeIdHolder;
import org.rec.planets.mercury.communication.bean.pack.JobPack;
import org.rec.planets.mercury.communication.bean.pack.ResultPack;
import org.rec.planets.mercury.communication.bean.snapshot.JobResultSnapshot;
import org.rec.planets.mercury.communication.service.FetchResultService;
import org.rec.planets.mercury.domain.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchResultServiceImpl implements FetchResultService {
	private static final Logger logger = LoggerFactory
			.getLogger(FetchResultServiceImpl.class);
	private Map<Short, Slot> slots;
	private CommandHandler commandHandler;

	@Override
	public ResultPack fetch(JobPack jobPack) {
		try {
			commandHandler.handle(jobPack.getCommand());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResultPack result = new ResultPack();
		result.setNodeId(NodeIdHolder.getNodeId());
		result.setJobResultSnapshots(new ArrayList<JobResultSnapshot>(jobPack
				.getJobs().size()));

		for (Job job : jobPack.getJobs()) {
			Slot slot = slots.get(job.getWebsiteId());
			if (slot == null) {
				logger.error("no slot config for websiteId "
						+ job.getWebsiteId());
				continue;
			}
			result.getJobResultSnapshots().add(slot.runJob(job));
		}
		return result;
	}

	public void setSlots(Map<Short, Slot> slots) {
		this.slots = slots;
	}

	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}
}
