package org.rec.planets.jupiter.context.accessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.mercury.expression.JexlUtil;
import org.springframework.util.Assert;

public class CollectionContextWriter implements ContextWriter {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void write(ActionContext context, String key, Object result) {
		Object obj = JexlUtil.evalFromObject(context, key);
		Assert.notNull(obj, "collection asserted not be null of key " + key
				+ " from crawlURL " + context.getCrawlURL());
		Assert.isInstanceOf(
				Collection.class,
				obj,
				"collection asserted be of Type Collection ,but is "
						+ obj.getClass() + " of key " + key + " from crawlURL "
						+ context.getCrawlURL());
		((Collection) obj).add(result);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object remove(ActionContext context, String key) {
		Object obj = JexlUtil.evalFromObject(context, key);
		Assert.notNull(obj, "collection asserted not be null of key " + key
				+ " from crawlURL " + context.getCrawlURL());
		Assert.isInstanceOf(
				Collection.class,
				obj,
				"collection asserted be of Type Collection ,but is "
						+ obj.getClass() + " of key " + key + " from crawlURL "
						+ context.getCrawlURL());

		Collection c = (Collection) obj;
		List<Object> list = new ArrayList<Object>();
		list.addAll(c);
		c.clear();
		return list;
	}
}
