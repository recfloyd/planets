package org.rec.planets.mercury.tree;

import org.rec.planets.mercury.expression.ELUtil;


/**
 * 根据EL表达式确定可用性的节点
 * 
 * @author rec
 * 
 * @param <C>
 * @param <V>
 */
public class ElReferenceTreeNode<C, V> extends
		AbstractExpressionReferenceTreeNode<C, V> {
	@Override
	protected Boolean evaluate(C context, String expression) {
		return (Boolean) ELUtil.evalFromObject(context, this.expression);
	}
}
