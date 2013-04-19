package org.rec.planets.jupiter.communication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.rec.planets.jupiter.slot.Slot;
import org.rec.planets.jupiter.slot.SlotFactory;
import org.rec.planets.jupiter.system.command.CommandHandler;
import org.rec.planets.jupiter.system.node.NodeIdHolder;
import org.rec.planets.mercury.communication.bean.pack.JobPack;
import org.rec.planets.mercury.communication.bean.pack.ResultPack;
import org.rec.planets.mercury.communication.bean.snapshot.JobResultSnapshot;
import org.rec.planets.mercury.communication.service.FetchResultService;
import org.rec.planets.mercury.domain.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取结果实现类<br/>
 * <code>
 * 	<bean id="获取结果Impl" class="">
 * 		<property name="slotFactory" ref="任务槽工厂"/>
 * 		<property name="commandHandler" ref="命令处理类"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
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
		result.setJobResultSnapshots(new ArrayList<JobResultSnapshot>());

		List<Job> jobs = jobPack.getJobs();
		if (jobs != null) {
			Slot slot = null;
			for (Job job : jobs) {
				try {
					slot = slotFactory.getSlot(job.getWebsiteId(), null);
				} catch (Exception e) {
					logger.error("no slot config for websiteId "
							+ job.getWebsiteId());
					continue;
				}
				if (slot == null) {
					logger.error("no slot config for websiteId "
							+ job.getWebsiteId());
					continue;
				}
				result.getJobResultSnapshots().add(slot.runJob(job));
			}
		}

		return result;
	}

	public void setSlotFactory(SlotFactory slotFactory) {
		this.slotFactory = slotFactory;
	}

	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}
}
