package org.rec.planets.jupiter.action.network.stat;

import java.net.HttpCookie;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.JexlContextReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class ResponseDebugAction extends AbstractResponseReadable implements
		Action, InitializingBean {
	private static final Logger logger = LoggerFactory
			.getLogger(ResponseDebugAction.class);

	@Override
	public void execute(ActionContext context) throws Exception {
		Response<?> response = super.getResponse(context);
		if (response == null) {
			logger.info("no response");
			return;
		}

		StringBuilder sb = new StringBuilder("response:\n");

		List<HttpCookie> cookies = response.getCookies();
		if (CollectionUtils.isEmpty(cookies)) {
			sb.append("no cookies\n");
		} else {
			sb.append("cookies:\n");
			for (HttpCookie hc : cookies) {
				sb.append(hc.getName()).append(" -> ").append(hc.getValue())
						.append('\n');
			}
		}

		ResponseEntity<?> entity = response.getHttpResponse();
		if (entity == null) {
			sb.append("no entity\n");
		} else {
			sb.append("status=").append(entity.getStatusCode().value())
					.append('\n');

			HttpHeaders headers = entity.getHeaders();
			if (headers.isEmpty()) {
				sb.append("no headers\n");
			} else {
				sb.append("headers:\n");
				for (Map.Entry<String, String> entry : headers
						.toSingleValueMap().entrySet()) {
					sb.append(entry.getKey()).append(" -> ")
							.append(entry.getValue()).append('\n');
				}
			}

			Object body = entity.getBody();
			if (body == null) {
				sb.append("no body\n");
			} else {
				sb.append("body class:").append(body.getClass());
				sb.append('\n').append(body).append('\n');
			}
		}

		logger.info(sb.toString());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.omitNull = true;
		this.resultKey = "urlcontext['key_http_response']";
		this.contextReader = new JexlContextReader();
	}

}
