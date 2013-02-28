package org.rec.planets.jupiter.action.workflow.iterable;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;

/**
 * 循环处理器 循环体存在于ActionContext内,通过一个key将其获取
 * 
 * @author rec
 * 
 */
public class ContextedIterableAction extends AbstractIterableAction {
	private ContextReader contextReader;
	private String itemsKey;

	@Override
	protected Object getItems(ActionContext context) {
		return contextReader.read(context, itemsKey);
	}

	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

	public void setItemsKey(String itemsKey) {
		this.itemsKey = itemsKey;
	}
}
