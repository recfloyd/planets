package org.rec.planets.jupiter.action.network.client.factory;

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
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.hc4.HC4Client;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.ActionContextConstants;

/**
 * HttpClient4的创建工厂
 * 
 * @author rec
 * 
 */
public class HC4ClientFactory implements ClientFactory {

	@SuppressWarnings("unchecked")
	@Override
	public Client getClient(ActionContext cotext) {
		Map<String, Object> clientParam = null;
		Map<String, Object> websitePropertie = cotext.getWebsiteProperties();
		if (websitePropertie != null)
			clientParam = (Map<String, Object>) websitePropertie
					.get(ActionContextConstants.KEY_CLIENT_PARAM);

		ClientConnectionManager multiThreadCollectionManager = new PoolingClientConnectionManager();
		DefaultHttpClient httpClient = new DefaultHttpClient(
				multiThreadCollectionManager);
		if (clientParam != null)
			for (Map.Entry<String, Object> entry : clientParam.entrySet()) {
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

		return new HC4Client(httpClient);
	}

}
