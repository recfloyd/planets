package org.rec.planets.jupiter.context.accessor;

import org.rec.planets.jupiter.context.ActionContext;

/**
 *上下文写入器
 * @author rec
 *
 */
public interface ContextWriter {
	void write(ActionContext context,String key, Object result);
}
