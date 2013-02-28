package org.rec.planets.jupiter.action.assemble;

import java.util.LinkedList;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextWriter;
import org.rec.planets.mercury.communication.bean.CrawlPropagation;
import org.rec.planets.mercury.domain.CrawlURL;

public class PreInitCrawlPropergationAssembler implements Action {
	private ContextWriter contextWriter;
	private String resultKey;
	@Override
	public void execute(ActionContext context) throws Exception {
		CrawlPropagation propagation=new CrawlPropagation();
		propagation.setParentId(context.getCrawlURL().getId());
		propagation.setChildren(new LinkedList<CrawlURL>());
		contextWriter.write(context, resultKey, propagation);
	}
	public void setContextWriter(ContextWriter contextWriter) {
		this.contextWriter = contextWriter;
	}
	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

}
