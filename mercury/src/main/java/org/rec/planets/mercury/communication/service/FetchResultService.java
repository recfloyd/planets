package org.rec.planets.mercury.communication.service;

import org.rec.planets.mercury.communication.bean.pack.JobPack;
import org.rec.planets.mercury.communication.bean.pack.ResultPack;

/**
 * master --> slave 发布任务并等待结果返回
 * 
 * @author rec
 * 
 */
public interface FetchResultService {
	ResultPack fetch(JobPack jobPack);
}
