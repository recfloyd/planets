package org.rec.planets.mercury.concurrent;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.Uninterruptibles;

/**
 * 每隔固定周期运行的自旋线程
 * 
 * @author rec
 * 
 */
public abstract class IntervaledThread extends Thread {
	private static final Logger logger = LoggerFactory
			.getLogger(IntervaledThread.class);
	private long interval;// 周期
	private TimeUnit timeUnit;// 时间单位
	private IntervalThreadExeptionHandler exeptionHandler;// 异常处理器

	public IntervaledThread(long interval) {
		this(interval, TimeUnit.SECONDS, null);
	}

	public IntervaledThread(long interval,
			IntervalThreadExeptionHandler exeptionHandler) {
		this(interval, TimeUnit.SECONDS, exeptionHandler);
	}

	public IntervaledThread(long interval, TimeUnit timeUnit,
			IntervalThreadExeptionHandler exeptionHandler) {
		this.interval = interval;
		this.timeUnit = timeUnit;
		this.exeptionHandler = exeptionHandler;
	}

	@Override
	public void run() {
		while (!this.isInterrupted()) {
			try {
				runInner();
			} catch (Exception e) {
				if (exeptionHandler == null) {
					logger.error(
							"no exception handler configured in recycling thread, exit",
							e);
					return;
				} else {
					try {
						exeptionHandler.handle(e, this);
					} catch (Exception e1) {
						logger.error(
								"could not handle the exeption in recycling thread, exit",
								e1);
						return;
					}
				}
			}
			Uninterruptibles.sleepUninterruptibly(interval, timeUnit);
		}
		super.run();
	}

	protected abstract void runInner() throws Exception;
}
