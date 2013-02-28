package org.rec.planets.jupiter.action.workflow.iterable.bean;

import org.rec.planets.mercury.domain.AbstractBean;

/**
 * 循环实体
 * 使用在循环处理器内
 * @author rec
 *
 */
public class IterableItem extends AbstractBean {
	private Object target;
	private int index;
	private int total;
	private boolean hasNext;
	private boolean first;
	private boolean last;

	public IterableItem(Object target, int index, int total, boolean hasNext,
			boolean first, boolean last) {
		this.target = target;
		this.index = index;
		this.total = total;
		this.hasNext = hasNext;
		this.first = first;
		this.last = last;
	}

	public Object getTarget() {
		return target;
	}

	public int getIndex() {
		return index;
	}

	public int getTotal() {
		return total;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public boolean isFirst() {
		return first;
	}

	public boolean isLast() {
		return last;
	}
}
