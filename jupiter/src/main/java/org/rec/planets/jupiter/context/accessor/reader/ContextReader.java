package org.rec.planets.jupiter.context.accessor.reader;


/**
 * 上下文读取器
 * 
 * @author rec
 * 
 */
public interface ContextReader {
	Object read(Object context,String key);
}
