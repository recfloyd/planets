package org.rec.planets.jupiter.processor;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.mercury.tree.TreeNode;
import org.rec.planets.mercury.tree.TreeNodeTraversalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 树形处理器,用于在运行过程中进行分支
 * @author rec
 *
 */
public class TreedCrawlProcessor implements CrawlProcessor {
	private static final Logger logger = LoggerFactory
			.getLogger(TreedCrawlProcessor.class);
	private TreeNode<CrawlContext, CrawlProcessor> tree;
	private boolean allowNotFound;

	@Override
	public void process(CrawlContext crawlContext) throws Exception {
		CrawlProcessor processor = TreeNodeTraversalUtil.traverse(tree,
				crawlContext);
		if (processor == null) {
			if (allowNotFound) {
				logger.warn("cannot find processor and quit process, for crawlURL: "
						+ crawlContext.getCrawlURL());
				return;
			} else {
				throw new RuntimeException(
						"cannot find processor for crawlURL: "
								+ crawlContext.getCrawlURL());
			}
		}
		processor.process(crawlContext);
	}

	public void setTree(TreeNode<CrawlContext, CrawlProcessor> tree) {
		this.tree = tree;
	}

	public void setAllowNotFound(boolean allowNotFound) {
		this.allowNotFound = allowNotFound;
	}

}
