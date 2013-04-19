package org.rec.planets.jupiter.context.accessor.writer;


/**
 * 上下文写入器
 * 
 * @author rec
 * 
 */
public interface ContextWriter {
	void write(Object context, String key, Object result);

	Object remove(Object context, String key);
}
