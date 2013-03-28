package org.rec.planets.mercury.expression;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public abstract class MvelUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(MvelUtil.class);

	private static LoadingCache<String, Serializable> expressionCache = CacheBuilder
			.newBuilder().concurrencyLevel(16).initialCapacity(1)
			.maximumSize(512).softValues()
			.build(new CacheLoader<String, Serializable>() {
				@Override
				public Serializable load(String expression) throws Exception {
					return MVEL.compileExpression(expression);
				}
			});

	public static Object evalFromObject(Object object, String expression) {
		Serializable compiled = null;
		try {
			compiled = expressionCache.get(expression);
		} catch (ExecutionException e1) {
			logger.error("error when getting expression", e1);
			return null;
		}
		return MVEL.executeExpression(compiled, object);
	}

	public static Object runScript(Map<String, Object> bindings, String script) {
		Serializable compiled = null;
		try {
			compiled = expressionCache.get(script);
		} catch (ExecutionException e1) {
			logger.error("error when getting expression", e1);
			return null;
		}
		VariableResolverFactory factory = new MapVariableResolverFactory(
				bindings);
		return MVEL.executeExpression(compiled, factory);
	}
}
