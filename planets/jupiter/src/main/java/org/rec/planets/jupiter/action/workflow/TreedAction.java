package org.rec.planets.jupiter.action.workflow;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.mercury.tree.TreeNode;
import org.rec.planets.mercury.tree.TreeNodeTraversalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 树形处理器,用于在运行过程中进行分支
 * @author rec
 *
 */
public class TreedAction implements Action {
	private static final Logger logger = LoggerFactory
			.getLogger(TreedAction.class);
	private TreeNode<ActionContext, Action> tree;
	private boolean allowNotFound;

	@Override
	public void execute(ActionContext context) throws Exception {
		Action action = TreeNodeTraversalUtil.traverse(tree,
				context);
		if (action == null) {
			if (allowNotFound) {
				logger.warn("cannot find action and quit process, for crawlURL: "
						+ context.getCrawlURL());
				return;
			} else {
				throw new RuntimeException(
						"cannot find action for crawlURL: "
								+ context.getCrawlURL());
			}
		}
		action.execute(context);
	}

	public void setTree(TreeNode<ActionContext, Action> tree) {
		this.tree = tree;
	}

	public void setAllowNotFound(boolean allowNotFound) {
		this.allowNotFound = allowNotFound;
	}

}
