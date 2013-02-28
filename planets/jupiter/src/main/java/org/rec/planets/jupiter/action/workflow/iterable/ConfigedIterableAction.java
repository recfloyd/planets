package org.rec.planets.jupiter.action.workflow.iterable;

import org.rec.planets.jupiter.context.ActionContext;

/**
 * 循环处理器
 * 通过配置的方式注入循环体
 * @author rec
 *
 */
public class ConfigedIterableAction extends AbstractIterableAction {
	private Object items;

	public void setItems(Object items) {
		this.items = items;
	}

	@Override
	protected Object getItems(ActionContext context) {
		return items;
	}

}
