package org.rec.planets.jupiter.action;

import org.rec.planets.jupiter.context.ActionContext;

/**
 * 处理器
 * @author rec
 *
 */
public interface Action {
	void execute(ActionContext context) throws Exception;
}
