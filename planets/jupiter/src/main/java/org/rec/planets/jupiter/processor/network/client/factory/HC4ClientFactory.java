package org.rec.planets.jupiter.processor.network.client.factory;

import java.util.Map;

import org.rec.planets.jupiter.bean.CrawlContext;
import org.rec.planets.jupiter.bean.CrawlContextConstants;
import org.rec.planets.jupiter.processor.network.client.Client;
import org.rec.planets.jupiter.processor.network.client.hc4.HC4Client;

public class HC4ClientFactory implements ClientFactory {

	@SuppressWarnings("unchecked")
	@Override
	public Client getClient(CrawlContext crawlContext) {
		Map<String, Object> clientParam = null;
		Map<String, Object> websitePropertie = crawlContext
				.getWebsiteProperties();
		if (websitePropertie != null)
			clientParam = (Map<String, Object>) websitePropertie
					.get(CrawlContextConstants.KEY_CLIENT_PARAM);
		return new HC4Client(clientParam);
	}

}
