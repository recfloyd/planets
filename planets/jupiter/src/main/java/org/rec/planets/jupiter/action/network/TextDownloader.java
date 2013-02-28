package org.rec.planets.jupiter.action.network;

import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.action.network.client.Client;

/**
 * 文本下载器
 * @author rec
 *
 */
public class TextDownloader extends AbstractDownloader {

	@Override
	protected <T> Response<?> request(Client client, Request request)
			throws Exception {
		return client.requestText(request);
	}
}
