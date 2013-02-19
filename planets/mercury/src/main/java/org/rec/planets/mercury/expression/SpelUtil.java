package org.rec.planets.mercury.expression;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public final class SpelUtil {
	private SpelUtil() {
	}

	private static final Logger logger = LoggerFactory
			.getLogger(SpelUtil.class);
	private static final ExpressionParser parser = new SpelExpressionParser();

	private static LoadingCache<String, Expression> expressionCache = CacheBuilder
			.newBuilder().concurrencyLevel(16).initialCapacity(1)
			.maximumSize(512).softValues()
			.build(new CacheLoader<String, Expression>() {
				@Override
				public Expression load(String expression) throws Exception {
					return parser.parseExpression(expression);
				}
			});

	public static Object evalFromObject(Object object, String expression) {
		Expression e = null;
		try {
			e = expressionCache.get(expression);
		} catch (ExecutionException e1) {
			logger.error("error when getting expression", e1);
			return null;
		}
		return e.getValue(object);
	}
}
