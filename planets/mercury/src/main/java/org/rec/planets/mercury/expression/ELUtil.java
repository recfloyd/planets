package org.rec.planets.mercury.expression;

import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.jexl2.ObjectContext;

public final class ELUtil {
	private ELUtil() {
	}

	private static final JexlEngine engine = new JexlEngine();
	static {
		engine.setCache(2048);
		engine.setLenient(false);
		engine.setSilent(false);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object evalFromObject(Object object, String expression) {
		JexlContext context = new ObjectContext(engine, object);
		Expression e = engine.createExpression(expression);
		return e.evaluate(context);
	}

	public static Object evalFromMap(Map<String, Object> map, String expression) {
		JexlContext ctx = new MapContext(map);
		Expression e = engine.createExpression(expression);
		return e.evaluate(ctx);
	}
}
