package org.rec.planets.mercury.tree;

/**
 * 判定永远为真的树节点,一般适合做头节点
 * 
 * @author lijia
 * 
 * @param <C>
 * @param <V>
 */
public class MatchedTreeNode<C, V> extends AbstractValuedTreeNode<C, V> {

	@Override
	public boolean isMatch(C context) {
		return true;
	}
}
