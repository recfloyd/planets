package org.rec.planets.jupiter.processor.workflow.iterable;

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
			stack.clear();
	}
}
