package org.rec.planets.mercury.tree;

import org.rec.planets.mercury.expression.MvelUtil;

/**
 * 根据EL表达式确定可用性的节点
 * 
 * @author rec
 * 
 * @param <C>
 * @param <V>
 */
public class MvelReferenceTreeNode<C, V> extends
		AbstractExpressionReferenceTreeNode<C, V> {
	@Override
	protected Boolean evaluate(C context, String expression) {
		return (Boolean) MvelUtil.evalFromObject(context, this.expression);
	}
}
