package org.rec.planets.mercury.tree;

import org.rec.planets.mercury.expression.JexlUtil;


/**
 * 根据EL表达式确定可用性的节点
 * 
 * @author rec
 * 
 * @param <C>
 * @param <V>
 */
public class JexlReferenceTreeNode<C, V> extends
		AbstractExpressionReferenceTreeNode<C, V> {
	@Override
	protected Boolean evaluate(C context, String expression) {
		return (Boolean) JexlUtil.evalFromObject(context, this.expression);
	}
}
