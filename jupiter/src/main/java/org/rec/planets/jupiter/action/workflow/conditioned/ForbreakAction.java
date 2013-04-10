package org.rec.planets.jupiter.action.workflow.conditioned;

import java.util.List;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * <code>
 * 	<bean id="ForBreakAction" class="">
 * 		<property name="contextReader" ref="条件读取器"/>
 * 		<property name="evalString" ref="条件读取键"/>
 * 		<property name="actions">
 * 			<list>
 * 				...
 * 			</list>
 * 		</property>
 * 	</bean>
 * </code>
 * @author rec
 *
 */
public class ForbreakAction extends AbstractConditionedAction {
	private List<Action> actions;

	@Override
	public void execute(ActionContext context) throws Exception {
		for (Action action : actions) {
			action.execute(context);
			if (eval(context))
				break;
		}

	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
}
