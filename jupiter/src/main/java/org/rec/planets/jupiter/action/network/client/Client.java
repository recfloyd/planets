package org.rec.planets.jupiter.action.network.client;

import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.Response;

/**
 * http客户端
 * @author rec
 *
 */
public interface Client {
	Response<String> requestText(Request request) throws Exception;
	Response<byte[]> requestByteArray(Request request) throws Exception;
	void destroy();
}
