package org.rec.planets.jupiter.action.network.client;

import org.rec.planets.jupiter.context.ActionContext;

/**
 * http客户端工厂
 * @author rec
 *
 */
public interface ClientFactory {
	Client getClient(ActionContext context);
}
