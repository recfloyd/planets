package org.rec.planets.jupiter.action.assemble.factory;

import java.util.concurrent.ConcurrentHashMap;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.mercury.communication.bean.CrawlEntity;

public class CrawlEntityFactory implements BeanFactory {

	@Override
	public Object getBean(ActionContext context) {
		CrawlEntity entity = new CrawlEntity();
		entity.setParentId(context.getCrawlURL().getId());
		entity.setContent(new ConcurrentHashMap<String, Object>());
		return entity;
	}

}
