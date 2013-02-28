package org.rec.planets.jupiter.action.network.request;

import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.context.ActionContext;

/**
 * 请求实体创建器
 * @author rec
 *
 */
public interface RequestBuilder {
	Request build(ActionContext context) throws Exception;
}
