package org.rec.planets.jupiter.action.system;

import org.apache.commons.lang.math.RandomUtils;
import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

public class SleepAction implements Action {
	private String sleepMilliseconds;

	@Override
	public void execute(ActionContext context) throws Exception {
		String[] ss = sleepMilliseconds.split("\\,");
		long min = Long.parseLong(ss[0]);
		if (ss.length == 1) {
			Thread.sleep(min);
		} else {
			long max = Long.parseLong(ss[1]);
			long sleep = min + RandomUtils.nextInt((int) (max + 1 - min));
			Thread.sleep(sleep);
		}
	}

	public void setSleepMilliseconds(String sleepMilliseconds) {
		this.sleepMilliseconds = sleepMilliseconds;
	}
}
