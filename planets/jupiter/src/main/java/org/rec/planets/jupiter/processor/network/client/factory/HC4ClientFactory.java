package org.rec.planets.jupiter.processor.network.client.factory;

import java.util.Map;

import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;
import org.rec.planets.jupiter.processor.network.client.Client;
import org.rec.planets.jupiter.processor.network.client.hc4.HC4Client;

/**
 * HttpClient4的创建工厂
 * @author rec
 *
 */
public class HC4ClientFactory implements ClientFactory {

	@SuppressWarnings("unchecked")
	@Override
	public Client getClient(ActionContext cotext) {
		Map<String, Object> clientParam = null;
		Map<String, Object> websitePropertie = cotext
				.getWebsiteProperties();
		if (websitePropertie != null)
			clientParam = (Map<String, Object>) websitePropertie
					.get(ActionContextConstants.KEY_CLIENT_PARAM);
		return new HC4Client(clientParam);
	}

}
