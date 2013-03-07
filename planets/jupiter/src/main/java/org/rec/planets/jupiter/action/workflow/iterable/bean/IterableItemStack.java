package org.rec.planets.jupiter.action.workflow.iterable.bean;

import java.util.ArrayDeque;
import java.util.Deque;

import org.rec.planets.mercury.domain.AbstractBean;

/**
 * 用于存储循环实体的valueStack
 * @author rec
 *
 */
public class IterableItemStack extends AbstractBean {
	private Deque<IterableItem> stack;

	public IterableItemStack() {
		this.stack=new ArrayDeque<IterableItem>();
	}
	
	public void put(IterableItem item){
		this.stack.addLast(item);
	}
	
	public IterableItem get(){
		return this.stack.getLast();
	}
	
	public void pop(){
		this.stack.pollLast();
	}
}
