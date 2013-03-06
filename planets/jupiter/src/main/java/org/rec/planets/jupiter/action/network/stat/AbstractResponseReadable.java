package org.rec.planets.jupiter.action.network.stat;

import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextReader;

public abstract class AbstractResponseReadable {
	protected boolean omitNull;
	protected String resultKey;
	protected ContextReader contextReader;

	protected Response<?> getResponse(ActionContext context) {
		Response<?> result = (Response<?>) contextReader.read(context,
				resultKey);
		if (result == null && !omitNull)
			throw new RuntimeException("cannot get response by key "
					+ resultKey + " of crawlURL " + context.getCrawlURL());
		return result;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public void setContextReader(ContextReader contextReader) {
		this.contextReader = contextReader;
	}

	public void setOmitNull(boolean omitNull) {
		this.omitNull = omitNull;
	}
}
