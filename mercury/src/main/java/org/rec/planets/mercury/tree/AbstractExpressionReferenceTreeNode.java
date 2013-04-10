package org.rec.planets.mercury.tree;

/**
 * 根据一个表达式对上下文进行评估的树节点
 * 
 * @author lijia
 * 
 * @param <C>
 * @param <V>
 */
public abstract class AbstractExpressionReferenceTreeNode<C, V> extends
		AbstractValuedTreeNode<C, V> {
	protected String expression;

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public boolean isMatch(C context) {
		Boolean bool = this.evaluate(context, expression);
		return bool == null ? false : bool.booleanValue();
	}

	protected abstract Boolean evaluate(C context, String expression);
}
