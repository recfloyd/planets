package org.rec.planets.mercury.communication.service;

import java.util.Map;

import org.rec.planets.mercury.communication.bean.JobResult;


/**
 * 汇报服务
 * 
 * @author rec
 * 
 */
public interface PushResultService {
	Map<String,Object> push(JobResult jobResult);
}
