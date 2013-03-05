package org.rec.planets.mercury.communication.service;

import java.util.Map;

import org.rec.planets.mercury.communication.bean.JobResult;


/**
 * slave --> master 推送结果
 * 
 * @author rec
 * 
 */
public interface PushResultService {
	Map<String,Object> push(JobResult jobResult);
}
