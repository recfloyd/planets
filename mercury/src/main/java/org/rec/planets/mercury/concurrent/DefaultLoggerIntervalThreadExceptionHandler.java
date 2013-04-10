package org.rec.planets.mercury.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自旋线程异常处理器,简单的将异常打印到日志中
 * 
 * @author rec
 * 
 */
public class DefaultLoggerIntervalThreadExceptionHandler implements
		IntervalThreadExeptionHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultLoggerIntervalThreadExceptionHandler.class);

	@Override
	public void handle(Exception e, IntervaledThread thread) throws Exception {
		logger.error("exception in recycling thread " + thread.getName(), e);
	}

}
