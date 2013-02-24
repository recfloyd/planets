package org.rec.planets.jupiter.processor.network.client.factory;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.processor.network.client.Client;

public interface ClientFactory {
	Client getClient(CrawlContext crawlContext);
}
