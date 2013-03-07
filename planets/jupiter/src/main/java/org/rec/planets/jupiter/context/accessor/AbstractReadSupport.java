package org.rec.planets.jupiter.context.accessor;

import org.rec.planets.jupiter.context.ActionContext;

public abstract class AbstractReadSupport {
	protected ContextReader contextReader;
	protected String sourceKey;
	protected boolean omitSourceNull;
	
	protected Object getSource(ActionContext context) throws Exception {
		Object source = contextReader.read(context, sourceKey);

		if (source == null) {
			if (!omitSourceNull)
				throw new RuntimeException(
						"cannot get source from actionContext by key "
								+ sourceKey + " of crawlURL "
								+ context.getCrawlURL());
			return null;
		}
		return source;
	}
	
	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

	public void setOmitSourceNull(boolean omitSourceNull) {
		this.omitSourceNull = omitSourceNull;
	}
}
