package org.rec.planets.jupiter.system.command;

import java.util.List;
import java.util.Map;

public class ChainedCommandHandler implements CommandHandler {
	private List<CommandHandler> nestedHandlers;

	@Override
	public void handle(Map<String, Object> command) throws Exception {
		for (CommandHandler handler : nestedHandlers) {
			handler.handle(command);
		}
	}

	public void setNestedHandlers(List<CommandHandler> nestedHandlers) {
		this.nestedHandlers = nestedHandlers;
	}
}
