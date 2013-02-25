package org.rec.planets.jupiter.processor.network;

import org.rec.planets.jupiter.processor.network.bean.Request;
import org.rec.planets.jupiter.processor.network.bean.Response;
import org.rec.planets.jupiter.processor.network.client.Client;

/**
 * 字节数组下载器
 * @author rec
 *
 */
public class ByteArrayDownloader extends AbstractDownloader {

	@Override
	protected <T> Response<?> request(Client client, Request request)
			throws Exception {
		return client.requestByteArray(request);
	}

}
