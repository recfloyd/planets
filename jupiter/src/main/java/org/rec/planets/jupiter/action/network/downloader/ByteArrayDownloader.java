package org.rec.planets.jupiter.action.network.downloader;

import org.rec.planets.jupiter.action.network.bean.Request;
import org.rec.planets.jupiter.action.network.bean.Response;
import org.rec.planets.jupiter.action.network.client.Client;

/**
 * 字节数组下载器<br/>
 * <code>
 * 	<bean id="字节数组下载Action" class="">
 * 		<property name="clientReader" ref="客户端读取器"/>
 * 		<property name="clientKey" ref="客户端读取键"/>
 * 		<property name="requestReader" ref="请求读取器"/>
 * 		<property name="requestKey" ref="请求读取键"/>
 * 		<property name="responseWriter" ref="响应写入器"/>
 * 		<property name="responseKey" ref="响应写入键"/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class ByteArrayDownloader extends AbstractDownloader {
	private static final long serialVersionUID = 5307404690940856027L;

	@Override
	protected <T> Response<?> request(Client client, Request request)
			throws Exception {
		return client.requestByteArray(request);
	}

}
