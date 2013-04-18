package org.rec.planets.jupiter.action.workflow;

import java.util.List;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 链式处理器,将一连串的处理包装为一组<br/>
 * <code>
 * 	<bean id="链式Action" class="">
 * 		<property name="actions">
 * 			<list>
 * 				...
 * 			</list>
 * 		</property>
 * 		<property name="omitException" value="是否忽略异常"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class ChainedAction implements Action {
	private static final Logger logger = LoggerFactory
			.getLogger(ChainedAction.class);
	private List<Action> actions;
	private boolean omitException;

	@Override
	public void execute(ActionContext context) throws Exception {
		for (Action action : actions) {
			try {
				action.execute(context);
			} catch (Exception e) {
				if (omitException) {
					logger.warn(
							"error occured and omitted when process crawlURL: "
									+ context.getCrawlURL(), e);
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
}
