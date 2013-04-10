package org.rec.planets.mercury.tree;

import java.util.List;

/**
 * 用于规则判定的深度优先多叉树节点<br/>
 * 该节点绑定了一个验证条件,以决定它本身即子节点是否应该被处理<br/>
 * 该节点还配备一个默认条件,当该节点的所有子节点都不match,那么该节点就表示了一个与事实最接近的节点,可以用它近似代替
 * 
 * @author lijia
 *
 * @param <C> Context,用于评估验证条件的上下文
 * @param <V> 操作的返回值
 */
public abstract class TreeNode<C,V> {
	/**
	 * 子节点列表
	 */
	protected List<TreeNode<C,V> > children;
	/**
	 * 该节点上是否绑定了默认操作.默认操作的意思是,如果该节点match,但是其子节点都不match,那么该节点就表示了一个与事实最接近的节点
	 */
	protected boolean defaulted;

	public void setChildren(List<TreeNode<C,V> > children) {
		this.children = children;
	}

	public List<TreeNode<C,V> > getChildren() {
		return children;
	}

	public boolean isDefaulted() {
		return defaulted;
	}

	public void setDefaulted(boolean defaulted) {
		this.defaulted = defaulted;
	}

	/**
	 * 用于判断此节点分支是否可用
	 * 
	 * @return
	 */
	public abstract boolean isMatch(C context);

	/**
	 * 定义在此节点上的操作
	 */
	public abstract V process(C context);
}
