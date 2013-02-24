package org.rec.planets.jupiter.processor.network.client;

import org.rec.planets.jupiter.processor.network.bean.Request;
import org.rec.planets.jupiter.processor.network.bean.Response;

public interface Client {
	Response<String> requestString(Request request) throws Exception;
	Response<byte[]> requestByteArray(Request request) throws Exception;
	void destroy();
}
