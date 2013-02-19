package org.rec.planets.mercury.tree;

import java.util.Stack;

/**
 * 用于遍历多叉树的工具类
 * 
 * @author lijia
 * 
 */
public final class TreeNodeTraversalUtil {
	private TreeNodeTraversalUtil() {
	}

	/**
	 * 采用非递归算法对树进行深度优先遍历
	 * 
	 * @param rootNode
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <C, V> V traverse(TreeNode<C, V> rootNode, C context) {
		if (rootNode == null)
			return null;

		/*
		 * 构造堆栈,其中每个元素为一个二元数组,第一元为节点,第二元是一个boolean值,表示该节点的所有子节点是否都已遍历
		 */
		Stack<Object[]> stack = new Stack<Object[]>();

		// 首先将根节点入栈
		stack.push(new Object[] { rootNode, false });

		Object[] tuple = null;
		TreeNode<C, V> node = null;
		boolean allChildrenTraversed = false;
		TreeNode<C, V> child = null;
		int childrenCnt = 0;

		while (!stack.isEmpty()) {
			/*
			 * 先从堆栈中peek一个元组
			 */
			tuple = stack.peek();
			node = (TreeNode<C, V>) tuple[0];
			allChildrenTraversed = (Boolean) tuple[1];

			if (node.isMatch(context)) {// 如果此节点判定条件为真,则需要遍历此节点及其子节点

				childrenCnt = node.getChildren() == null ? 0 : node
						.getChildren().size();

				if (allChildrenTraversed) {// 如果该节点的子节点都已经处理完毕,将此节点出栈,如果它有默认操作则执行
					stack.pop();
					if (node.isDefaulted()) {
						return node.process(context);
					}
				} else if (childrenCnt == 0) {// 如果该节点无子节点,说明它是符合条件的叶子节点(最符合真相的节点),出栈后直接执行此节点上的操作
					stack.pop();
					return node.process(context);
				} else {// 如果该节点拥有子节点,将子节点倒序入栈,并将此节点的allChildrenTraversed标记为true
					for (int i = childrenCnt - 1; i > -1; i--) {
						child = node.getChildren().get(i);
						stack.push(new Object[] { child, false });
					}
					tuple[1] = true;
				}

			} else {// 如果此节点判定条件为假,则将此节点出栈
				stack.pop();
			}
		}

		return null;
	}
}
