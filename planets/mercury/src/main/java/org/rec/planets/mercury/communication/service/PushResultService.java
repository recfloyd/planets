package org.rec.planets.mercury.communication.service;

import org.rec.planets.mercury.communication.bean.JobResult;


/**
 * 汇报服务
 * 
 * @author rec
 * 
 */
public interface PushResultService {
	String push(JobResult jobResult);
}
