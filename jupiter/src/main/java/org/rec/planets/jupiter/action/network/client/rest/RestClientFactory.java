package org.rec.planets.jupiter.action.network.client.rest;

import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.ClientFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.web.client.RestTemplate;

public class RestClientFactory implements ClientFactory {

	@Override
	public Client getClient(ActionContext context) {
		RestClient client = new RestClient(new RestTemplate());
		return client;
	}

}
