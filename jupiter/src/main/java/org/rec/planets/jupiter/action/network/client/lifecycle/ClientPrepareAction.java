package org.rec.planets.jupiter.action.network.client.lifecycle;

import org.rec.planets.jupiter.action.Action;
import org.rec.planets.jupiter.action.network.client.Client;
import org.rec.planets.jupiter.action.network.client.ClientFactory;
import org.rec.planets.jupiter.context.ActionContext;
import org.rec.planets.jupiter.context.accessor.support.AbstractReadWriteSupport;

/**
 * 全局客户端准备器<br/>
 * <code>
 * 	<bean id="客户端准备Action" class="">
 * 		<property name="contextReader" ref="客户端读取器"/>
 * 		<property name="sourceKey" value="客户端读取键"/>
 * 		<property name="omitSourceNull" value="true"/>
 * 
 * 		<property name="contextWriter" ref="客户端写入器"/>
 * 		<property name="resultKey" value="客户端写入键"/>
 * 		<property name="omitResultNull" value="false"/>
 * 
 * 		<property name="clientCache" ref="..."/>
 * 		<property name="clientFactory" ref="..."/>
 * 	</bean>
 * </code>
 * 
 * @author rec
 * 
 */
public class ClientPrepareAction extends AbstractReadWriteSupport implements
		Action {
	private ClientCache clientCache;
	private ClientFactory clientFactory;

	@Override
	public void execute(ActionContext context) throws Exception {
		Client client = (Client) getSource(context);
		if (client == null) {
			client = clientCache.getClient(context, clientFactory);
			writeResult(context, client);
		}
	}

	public void setClientCache(ClientCache clientCache) {
		this.clientCache = clientCache;
	}

	public void setClientFactory(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
}
