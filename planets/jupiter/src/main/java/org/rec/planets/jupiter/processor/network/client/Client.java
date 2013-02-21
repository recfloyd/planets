package org.rec.planets.jupiter.processor.network.client;

import org.rec.planets.jupiter.processor.network.bean.Request;
import org.rec.planets.jupiter.processor.network.bean.Response;

public interface Client {
	<T> Response<T> request(Request request) throws Exception;
	void close();
}
