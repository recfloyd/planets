package org.rec.planets.jupiter.action.network.client.factory;

import java.util.Map;

import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.hc4.HC4Client;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;

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
