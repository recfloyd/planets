package org.rec.planets.jupiter.context.accessor.support;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.writer.ContextWriter;

public class AbstractReadWriteSupport extends AbstractReadSupport {
	protected ContextWriter contextWriter;
	protected String resultKey;
	protected boolean omitResultNull;

	protected void writeResult(ActionContext context, Object result)
			throws Exception {
		if (result == null) {
			if (!omitResultNull) {
				throw new RuntimeException(
						"cannot set result to actionContext because of null by key "
								+ resultKey + " of crawlURL "
								+ context.getCrawlURL());
			} else
				return;
		}
		contextWriter.write(context, resultKey, result);
	}

	protected void removeObject(ActionContext context) {
		contextWriter.remove(context, resultKey);
	}

	public void setContextWriter(ContextWriter contextWriter) {
		this.contextWriter = contextWriter;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public void setOmitResultNull(boolean omitResultNull) {
		this.omitResultNull = omitResultNull;
	}
}
