package org.rec.planets.jupiter.context.accessor.writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rec.planets.mercury.expression.MvelUtil;
import org.springframework.util.Assert;

public class CollectionContextWriter implements ContextWriter {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void write(Object context, String key, Object result) {
		Object obj = MvelUtil.evalFromObject(context, key);
		Assert.notNull(obj, "collection asserted not be null of key " + key);
		Assert.isInstanceOf(
				Collection.class,
				obj,
				"collection asserted be of Type Collection ,but is "
						+ obj.getClass() + " of key " + key);
		((Collection) obj).add(result);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object remove(Object context, String key) {
		Object obj = MvelUtil.evalFromObject(context, key);
		Assert.notNull(obj, "collection asserted not be null of key " + key);
		Assert.isInstanceOf(
				Collection.class,
				obj,
				"collection asserted be of Type Collection ,but is "
						+ obj.getClass() + " of key " + key);

		Collection c = (Collection) obj;
		List<Object> list = new ArrayList<Object>();
		list.addAll(c);
		c.clear();
		return list;
	}
}
