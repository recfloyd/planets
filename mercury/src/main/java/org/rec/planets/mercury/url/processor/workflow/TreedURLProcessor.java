package org.rec.planets.mercury.url.processor.workflow;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.CrawlURL;
import org.rec.planets.mercury.tree.TreeNode;
import org.rec.planets.mercury.tree.TreeNodeTraversalUtil;
import org.rec.planets.mercury.url.processor.URLProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 树形化的处理器
 * 
 * @author rec
 * 
 */
public class TreedURLProcessor extends AbstractBean implements URLProcessor {
	private static final long serialVersionUID = -69893626640602323L;
	private static final Logger logger = LoggerFactory
			.getLogger(TreedURLProcessor.class);
	private TreeNode<CrawlURL, URLProcessor> tree;
	private boolean allowNotFound;

	@Override
	public void process(CrawlURL crawlURL, CrawlURL baseURL) throws Exception {
		URLProcessor processor = TreeNodeTraversalUtil.traverse(tree, crawlURL);
		if (processor == null) {
			if (allowNotFound) {
				logger.warn("cannot find processor and quit process, for crawlURL: "
						+ crawlURL);
				return;
			} else {
				throw new RuntimeException(
						"cannot find processor for crawlURL: " + crawlURL);
			}
		}
		processor.process(crawlURL, baseURL);
	}

	public void setTree(TreeNode<CrawlURL, URLProcessor> tree) {
		this.tree = tree;
	}

	public void setAllowNotFound(boolean allowNotFound) {
		this.allowNotFound = allowNotFound;
	}
}
