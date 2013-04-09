package org.rec.planets.jupiter.action.workflow.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 并行处理器.它将一组可并行运行的处理器运行在一个线程池上,并等待所有处理结束之后再返回<br/>
 * <code>
 * 	<bean id="并行Action" class="">
 * 		<property name="actions">
 * 			<list>
 * 				...
 * 			</list>
 * 		</property>
 * 		<property name="threadPoolFactory" ref="并行线程池"/>
 * 		<property name="omitException" ref="是否忽略异常"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class ParallelAction implements Action {
	private static final Logger logger = LoggerFactory
			.getLogger(ParallelAction.class);
	private List<Action> actions;
	private boolean omitException;
	private ThreadPoolFactory threadPoolFactory;

	@Override
	public void execute(final ActionContext context) throws Exception {
		ExecutorService threadPool = threadPoolFactory.getThreadPool(context);
		List<Future<Void>> reaults = new ArrayList<Future<Void>>(actions.size());
		ActionFutureTask task = null;
		for (Action action : actions) {
			task = new ActionFutureTask(action, context);
			threadPool.execute(task);
			reaults.add(task);
		}
		for (int i = 0; i < reaults.size(); i++) {
			try {
				reaults.get(i).get();
			} catch (ExecutionException e) {
				if (omitException) {
					logger.warn(
							"error occured and omitted when process crawlURL: "
									+ context.getCrawlURL()
									+ " and action index is " + i
									+ " and action is " + actions.get(i), e);
					continue;
				} else {
					throw e;
				}
			}
		}
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void setOmitException(boolean omitException) {
		this.omitException = omitException;
	}

	public void setThreadPoolFactory(ThreadPoolFactory threadPoolFactory) {
		this.threadPoolFactory = threadPoolFactory;
	}
}
