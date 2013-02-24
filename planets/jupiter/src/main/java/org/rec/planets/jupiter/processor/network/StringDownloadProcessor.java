package org.rec.planets.jupiter.processor.network;

import org.rec.planets.jupiter.processor.network.bean.Request;
import org.rec.planets.jupiter.processor.network.bean.Response;
import org.rec.planets.jupiter.processor.network.client.Client;

public class StringDownloadProcessor extends AbstractDownloadProcessor {

	@Override
	protected <T> Response<?> request(Client client, Request request)
			throws Exception {
		return client.requestString(request);
	}
}
