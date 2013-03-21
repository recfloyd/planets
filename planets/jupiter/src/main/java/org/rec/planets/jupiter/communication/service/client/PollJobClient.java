package org.rec.planets.jupiter.communication.service.client;

import java.util.HashMap;
import java.util.Map;

import org.rec.planets.jupiter.slot.Slot;
import org.rec.planets.jupiter.system.NodeIdHolder;
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
	private Map<Short, Slot> slots;

	public void execute() throws Exception {
		PollPack pollPack = new PollPack();
		pollPack.setNodeId(NodeIdHolder.getNodeId());
		Map<Short, Long> ruleVersions = new HashMap<Short, Long>(slots.size());
		for (Map.Entry<Short, Slot> slotEntry : slots.entrySet()) {
			ruleVersions.put(slotEntry.getKey(), slotEntry.getValue()
					.getRuleVersion());
		}
		pollPack.setRuleVersions(ruleVersions);

		JobPack jobPack = pollJobService.poll(pollPack);

		if (jobPack == null)
			return;
		// TODO 首先处理命令

		for (Job job : jobPack.getJobs()) {
			Slot slot = slots.get(job.getWebsiteId());
			if (slot == null) {
				logger.error("no slot config for websiteId "
						+ job.getWebsiteId());
				continue;
			}
			slot.submitJob(job);
		}
	}

	public void setPollJobService(PollJobService pollJobService) {
		this.pollJobService = pollJobService;
	}

	public void setSlots(Map<Short, Slot> slots) {
		this.slots = slots;
	}
}
