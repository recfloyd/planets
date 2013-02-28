package org.rec.planets.jupiter.context.accessor;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.mercury.expression.SpelUtil;

public class SpelContextReader implements ContextReader {

	@Override
	public Object read(ActionContext context, String key) {
		return SpelUtil.evalFromObject(context, key);
	}

}
