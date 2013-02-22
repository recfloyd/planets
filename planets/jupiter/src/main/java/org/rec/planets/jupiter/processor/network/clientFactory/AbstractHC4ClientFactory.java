package org.rec.planets.jupiter.processor.network.clientFactory;

import java.io.IOException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

public abstract class AbstractHC4ClientFactory implements ClientFactory {
	protected DefaultHttpClient newClient(Map<String, ?> clientParam) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		if (clientParam != null)
			for (Map.Entry<String, ?> entry : clientParam.entrySet()) {
				if (entry.getValue() != null)
					httpClient.getParams().setParameter(entry.getKey(),
							entry.getValue());
			}

		// 添加gzip,deflate支持
		httpClient.addResponseInterceptor(new HttpResponseInterceptor() {

			@Override
			public void process(HttpResponse response, HttpContext arg1)
					throws HttpException, IOException {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					Header ceheader = entity.getContentEncoding();
					if (ceheader != null) {
						HeaderElement[] codecs = ceheader.getElements();
						for (int i = 0; i < codecs.length; i++) {
							if (codecs[i].getName().equalsIgnoreCase("gzip")) {
								response.setEntity(new GzipDecompressingEntity(
										entity));
								return;
							} else if (codecs[i].getName().equalsIgnoreCase(
									"deflate")) {
								response.setEntity(new DeflateDecompressingEntity(
										entity));
								return;
							}
						}
					}
				}
			}
		});

		return httpClient;
	}
}
