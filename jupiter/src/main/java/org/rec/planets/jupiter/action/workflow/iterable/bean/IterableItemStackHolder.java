package org.rec.planets.jupiter.action.workflow.iterable.bean;

import org.rec.planets.jupiter.context.accessor.writer.ThreadLocalObjectHolder;

/**
 * 循环实体保存器.它将循环实体保存在当前线程的ThreadLocal里面
 * 
 * @author rec
 * 
 */
public abstract class IterableItemStackHolder {
	public static final String KEY_ITERABLE_ITEM_STACK = "key_iterable_item_stack";

	public static final void putItem(IterableItem item) {
		IterableItemStack stack = (IterableItemStack) ThreadLocalObjectHolder
				.getObject(KEY_ITERABLE_ITEM_STACK);
		if (stack == null) {
			ThreadLocalObjectHolder.atomicInit(KEY_ITERABLE_ITEM_STACK);
			ThreadLocalObjectHolder.putObject(KEY_ITERABLE_ITEM_STACK,
					new IterableItemStack());
			stack = (IterableItemStack) ThreadLocalObjectHolder
					.getObject(KEY_ITERABLE_ITEM_STACK);
		}
		stack.put(item);
	}

	public static final IterableItem getItem() {
		IterableItemStack stack = (IterableItemStack) ThreadLocalObjectHolder
				.getObject(KEY_ITERABLE_ITEM_STACK);
		return stack == null ? null : stack.get();
	}

	public static final void popItem() {
		IterableItemStack stack = (IterableItemStack) ThreadLocalObjectHolder
				.getObject(KEY_ITERABLE_ITEM_STACK);
		if (stack != null)
			stack.pop();
	}
}
