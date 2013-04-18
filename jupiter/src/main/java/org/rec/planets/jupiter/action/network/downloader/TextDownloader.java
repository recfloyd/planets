package org.rec.planets.jupiter.action.network.downloader;

import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.action.network.client.Client;

/**
 * 文本下载器<br/>
 * <code>
 * 	<bean id="文本下载Action" class="">
 * 		<property name="clientReader" ref="客户端读取器"/>
 * 		<property name="clientKey" value="客户端读取键"/>
 * 		<property name="requestReader" ref="请求读取器"/>
 * 		<property name="requestKey" value="请求读取键"/>
 * 		<property name="responseWriter" ref="响应写入器"/>
 * 		<property name="responseKey" value="响应写入键"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class TextDownloader extends AbstractDownloader {
	private static final long serialVersionUID = 7869967846390496961L;

	@Override
	protected <T> Response<?> request(Client client, Request request)
			throws Exception {
		return client.requestText(request);
	}
}
