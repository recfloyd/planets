package org.rec.planets.jupiter.action.workflow.parallel;

import java.util.concurrent.ExecutorService;

import org.rec.planets.jupiter.context.ActionContext;

public interface ThreadPoolFactory {
	ExecutorService getThreadPool(ActionContext context);
}
