package org.rec.planets.jupiter.context.accessor;

import org.rec.planets.jupiter.context.ActionContext;

/**
 * 上下文读取器
 * 
 * @author rec
 * 
 */
public interface ContextReader {
	Object read(ActionContext context,String key);
}
