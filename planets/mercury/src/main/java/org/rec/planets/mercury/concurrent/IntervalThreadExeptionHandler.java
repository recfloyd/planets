package org.rec.planets.mercury.concurrent;

/**
 * 自旋线程,异常处理器
 * 
 * @author rec
 * 
 */
public interface IntervalThreadExeptionHandler {
	/**
	 * 处理异常
	 * 
	 * @param e
	 *            原始异常信息
	 * @param thread
	 *            执行线程
	 * @throws Exception
	 *             可以抛出新的异常
	 */
	void handle(Exception e, IntervaledThread thread) throws Exception;
}
