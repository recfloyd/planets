package org.rec.planets.jupiter.action.assemble;

import java.util.concurrent.ConcurrentHashMap;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.ContextWriter;
import org.rec.planets.mercury.communication.bean.CrawlEntity;

public class PreInitCrawlEntityAssembler implements Action {
	private ContextWriter contextWriter;
	private String resultKey;

	@Override
	public void execute(ActionContext context) throws Exception {
		CrawlEntity entity = new CrawlEntity();
		entity.setParentId(context.getCrawlURL().getId());
		entity.setContent(new ConcurrentHashMap<String, Object>());
		contextWriter.write(context, resultKey, entity);
	}

	public void setContextWriter(ContextWriter contextWriter) {
		this.contextWriter = contextWriter;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}
}
