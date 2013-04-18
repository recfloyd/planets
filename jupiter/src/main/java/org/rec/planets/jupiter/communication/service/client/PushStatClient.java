package org.rec.planets.jupiter.communication.service.client;

import java.util.Map;

import org.rec.planets.jupiter.slot.SlotFactory;
import org.rec.planets.jupiter.system.command.CommandHandler;
import org.rec.planets.jupiter.system.node.NodeIdHolder;
import org.rec.planets.mercury.communication.bean.pack.StatPack;
import org.rec.planets.mercury.communication.service.PushStatService;

/**
 * 推送统计信息客户端<br/>
 * <code>
 * 	<bean id="推送统计信息Client" class="">
 * 		<property name="pushResultService" ref="推送统计信息服务实现类"/>
 * 		<property name="slotFactory" ref="任务槽工厂"/>
 * 		<property name="commandHandler" ref="命令处理类"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class PushStatClient {
	private SlotFactory slotFactory;
	private PushStatService pushStatService;
	private CommandHandler commandHandler;

	public void execute() throws Exception {
		StatPack statPack = new StatPack();
		statPack.setNodeId(NodeIdHolder.getNodeId());
		statPack.setCurrentJobSnapshots(slotFactory.getCurrentJobSnapshots());
		statPack.setWebsiteSnapshots(slotFactory.getWebsiteSnapshots());

		Map<String, Object> command = pushStatService.push(statPack);
		if (command != null && command.size() > 0) {
			if (commandHandler == null) {
				throw new RuntimeException(
						"there's no command hanlder for command " + command);
			}
			commandHandler.handle(command);
		}
	}

	public void setSlotFactory(SlotFactory slotFactory) {
		this.slotFactory = slotFactory;
	}

	public void setPushStatService(PushStatService pushStatService) {
		this.pushStatService = pushStatService;
	}

	public void setCommandHandler(CommandHandler commandHandler) {
		this.commandHandler = commandHandler;
	}
}
