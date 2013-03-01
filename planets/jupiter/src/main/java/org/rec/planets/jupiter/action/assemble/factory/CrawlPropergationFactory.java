package org.rec.planets.jupiter.action.assemble.factory;

import java.util.LinkedList;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.mercury.communication.bean.CrawlPropagation;
import org.rec.planets.mercury.domain.CrawlURL;

public class CrawlPropergationFactory implements BeanFactory {

	@Override
	public Object getBean(ActionContext context) {
		CrawlPropagation propagation=new CrawlPropagation();
		propagation.setParentId(context.getCrawlURL().getId());
		propagation.setChildren(new LinkedList<CrawlURL>());
		return propagation;
	}

}
