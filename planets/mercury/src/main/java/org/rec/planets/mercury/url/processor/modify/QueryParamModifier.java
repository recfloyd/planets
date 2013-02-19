package org.rec.planets.mercury.url.processor.modify;

import java.util.Set;

import org.rec.planets.mercury.parse.URLUtil;

/**
 * 通过配置对参数进行取舍的转化器
 * 
 * @author rec
 * 
 */
public class QueryParamModifier extends AbstractModifier {
	/**
	 * 参数名列表
	 */
	private Set<String> params;
	/**
	 * 是否保留,默认是去掉
	 */
	private boolean reserved;

	@Override
	public String modify(String url, String baseURL) {
		return URLUtil.stripQueryParam(url, params, reserved);
	}

	public void setParams(Set<String> params) {
		this.params = params;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
}
