package org.rec.planets.jupiter.action.network.client.rest;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.ClientFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestClientFactory implements ClientFactory {
	private String charset;

	@Override
	public Client getClient(ActionContext context) {
		RestTemplate template = new RestTemplate();
		if (StringUtils.isNotBlank(charset)) {
			List<HttpMessageConverter<?>> converters = template
					.getMessageConverters();
			Iterator<HttpMessageConverter<?>> it = converters.iterator();
			while (it.hasNext()) {
				HttpMessageConverter<?> converter = it.next();
				if (converter.getClass().equals(
						StringHttpMessageConverter.class)) {
					it.remove();
					break;
				}
			}
			converters.add(0,
					new StringHttpMessageConverter(Charset.forName(charset)));
		}
		RestClient client = new RestClient(template);
		return client;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}
