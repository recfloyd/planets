package org.rec.planets.jupiter.communication.service;

import org.rec.planets.jupiter.slot.SlotFactory;
import org.rec.planets.jupiter.system.node.NodeIdHolder;
import org.rec.planets.mercury.communication.bean.pack.StatPack;
import org.rec.planets.mercury.communication.service.FetchStatService;

/**
 * 获取统计信息实现类<br/>
 * <code>
 * 	<bean id="获取统计信息Client" class="">
 * 		<property name="slotFactory" ref="任务槽工厂"/>
 * 	</bean>
 * </code>
 * @author rec
 *
 */
public class FetchStatServiceImpl implements FetchStatService {
	private SlotFactory slotFactory;

	@Override
	public StatPack fetch() {
		StatPack result = new StatPack();
		result.setNodeId(NodeIdHolder.getNodeId());
		result.setCurrentJobSnapshots(slotFactory.getCurrentJobSnapshots());
		result.setWebsiteSnapshots(slotFactory.getWebsiteSnapshots());
		return result;
	}

	public void setSlotFactory(SlotFactory slotFactory) {
		this.slotFactory = slotFactory;
	}
}
