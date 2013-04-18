package org.rec.planets.jupiter.system.command;

import java.util.Map;

public interface CommandHandler {
	void handle(Map<String, Object> command) throws Exception;
}
