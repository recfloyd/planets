package org.rec.planets.jupiter.action;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;
import org.rec.planets.jupiter.context.accessor.ContextWriter;

/**
 * 抽象的读写处理器 根据CrawlContextAccessor从crawlContext中读取数据,处理后再回写结果
 * 
 * @author rec
 * 
 */
public abstract class AbstractReadWriteAction implements Action {
	protected ContextReader contextReader;
	protected ContextWriter contextWriter;
	protected String sourceKey;
	protected String resultKey;
	protected boolean omitSourceNull;
	protected boolean omitResultNull;

	protected abstract Object processInternal(ActionContext context,
			Object source) throws Exception;

	@Override
	public void execute(ActionContext context) throws Exception {
		Object source = contextReader.read(context, resultKey);

		if (source == null) {
			if (!omitSourceNull)
				throw new RuntimeException(
						"cannot get source from actionContext by key "
								+ sourceKey + " of crawlURL "
								+ context.getCrawlURL());
			return;
		}

		Object result = this.processInternal(context, source);

		if (result == null) {
			if (!omitResultNull)
				throw new RuntimeException(
						"cannot set result to actionContext because of null by key "
								+ resultKey + " of crawlURL "
								+ context.getCrawlURL());
			return;
		}

		contextWriter.write(context, resultKey, result);
	}

	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

	public void setContextWriter(ContextWriter contextWriter) {
		this.contextWriter = contextWriter;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public void setOmitSourceNull(boolean omitSourceNull) {
		this.omitSourceNull = omitSourceNull;
	}

	public void setOmitResultNull(boolean omitResultNull) {
		this.omitResultNull = omitResultNull;
	}
}
