package org.rec.planets.jupiter.system.command;

import java.util.Map;

import org.rec.planets.jupiter.slot.SlotFactory;

public class RuleUpdateCommandHandler implements CommandHandler {
	public static final String COMMAND_KEY = "update_rules";
	private SlotFactory slotFactory;

	@Override
	public void handle(Map<String, Object> command) throws Exception {
		@SuppressWarnings("unchecked")
		Map<Short, String[]> updateRules = (Map<Short, String[]>) (command
				.get(COMMAND_KEY));
		if (updateRules == null)
			return;

		for (Map.Entry<Short, String[]> entry : updateRules.entrySet()) {
			slotFactory.updateRule(entry.getKey(), entry.getValue());
		}
	}

	public void setSlotFactory(SlotFactory slotFactory) {
		this.slotFactory = slotFactory;
	}

}
