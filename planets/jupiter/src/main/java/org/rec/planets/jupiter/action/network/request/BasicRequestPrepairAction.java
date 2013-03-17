package org.rec.planets.jupiter.action.network.request;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.RequestMethod;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.AbstractReadWriteSupport;

public class BasicRequestPrepairAction extends AbstractReadWriteSupport
		implements Action {

	@Override
	public void execute(ActionContext context) throws Exception {
		Request request = new Request();
		request.setUrl(context.getCrawlURL().getUrl());
		request.setMethod(RequestMethod.GET);
		writeResult(context, request);
	}

}
