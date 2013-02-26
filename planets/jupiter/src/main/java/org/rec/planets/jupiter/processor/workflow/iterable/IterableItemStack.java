package org.rec.planets.jupiter.processor.workflow.iterable;

import java.util.ArrayDeque;
import java.util.Deque;

import org.rec.planets.mercury.domain.AbstractBean;

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
	
	public void clear(){
		this.stack.pollLast();
	}
}
