package org.rec.planets.jupiter.action;

import org.rec.planets.jupiter.context.ActionContext;

/**
 * 处理器
 * 处理器是运行在线程池中的,所以应该具备响应中断的能力
 * 同时处理器由RuleFactory创建,应该具备其销毁时回收资源的能力
 * @author rec
 *
 */
public interface Action {
	void execute(ActionContext context) throws Exception;
}
