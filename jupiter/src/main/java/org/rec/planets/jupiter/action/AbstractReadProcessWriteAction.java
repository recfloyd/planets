package org.rec.planets.jupiter.action;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.support.AbstractReadWriteSupport;

/**
 * 抽象的读写处理器 根据CrawlContextAccessor从crawlContext中读取数据,处理后再回写结果
 * 
 * @author rec
 * 
 */
public abstract class AbstractReadProcessWriteAction extends
		AbstractReadWriteSupport implements Action {
	protected abstract Object processInternal(ActionContext context,
			Object source) throws Exception;

	@Override
	public void execute(ActionContext context) throws Exception {
		Object source = getSource(context);

		if (source != null) {
			Object result = processInternal(context, source);
			writeResult(context, result);
		}
	}
}
