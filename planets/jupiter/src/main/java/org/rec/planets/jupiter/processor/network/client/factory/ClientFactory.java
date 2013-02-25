package org.rec.planets.jupiter.processor.network.client.factory;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.network.client.Client;

/**
 * http客户端工厂
 * @author rec
 *
 */
public interface ClientFactory {
	Client getClient(CrawlContext crawlContext);
}
