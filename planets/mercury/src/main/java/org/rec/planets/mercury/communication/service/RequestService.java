package org.rec.planets.mercury.communication.service;

import org.rec.planets.mercury.communication.bean.RequestEntity;
import org.rec.planets.mercury.communication.bean.ResponseInfo;

/**
 * 请求服务
 * 
 * @author rec
 * 
 */
public interface RequestService {
	ResponseInfo requestJob(RequestEntity requestEntity);
}
