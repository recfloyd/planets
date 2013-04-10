package org.rec.planets.mercury.tree;

/**
 * 可设置一个给定返回值的树节点
 * @author lijia
 *
 * @param <C>
 * @param <V>
 */
public abstract class AbstractValuedTreeNode<C, V> extends TreeNode<C, V> {
	public AbstractValuedTreeNode() {
		super();
		this.defaulted = true;
	}

	protected V value;

	public void setValue(V value) {
		this.value = value;
	}

	@Override
	public V process(C context) {
		return value;
	}
}
