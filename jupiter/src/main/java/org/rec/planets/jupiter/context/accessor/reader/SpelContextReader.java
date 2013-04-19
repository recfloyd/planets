package org.rec.planets.jupiter.context.accessor.reader;

import org.rec.planets.mercury.expression.SpelUtil;

public class SpelContextReader implements ContextReader {

	@Override
	public Object read(Object context, String key) {
		return SpelUtil.evalFromObject(context, key);
	}

}
