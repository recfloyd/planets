package org.rec.planets.jupiter.action.assemble;

import org.rec.planets.jupiter.action.AbstractReadWriteAction;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 属性设置组装器,它从某个地方读取一个值,不做任何处理直接复制到另外一个地方
 * 
 * @author rec
 * 
 */
public class PropertySetAssambler extends AbstractReadWriteAction {

	@Override
	protected Object processInternal(ActionContext context, Object source)
			throws Exception {
		return source;
	}

}
