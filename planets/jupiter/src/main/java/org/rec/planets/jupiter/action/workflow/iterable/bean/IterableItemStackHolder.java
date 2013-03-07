package org.rec.planets.jupiter.action.workflow.iterable.bean;

/**
 * 循环实体保存器.它将循环实体保存在当前线程的ThreadLocal里面
 * @author rec
 *
 */
public abstract class IterableItemStackHolder {
	private static final ThreadLocal<IterableItemStack> THREAD_LOCAL = new ThreadLocal<IterableItemStack>();

	public static final void putItem(IterableItem item) {
		IterableItemStack stack = THREAD_LOCAL.get();
		if (stack == null) {
			stack = new IterableItemStack();
			THREAD_LOCAL.set(stack);
		}
		stack.put(item);
	}

	public static final IterableItem getItem() {
		IterableItemStack stack = THREAD_LOCAL.get();
		if (stack == null)
			return null;
		else
			return stack.get();
	}

	public static final void popItem() {
		IterableItemStack stack = THREAD_LOCAL.get();
		if (stack != null)
			stack.pop();
	}
}
