package org.rec.planets.mercury.communication.service;

import org.rec.planets.mercury.communication.bean.JobInfo;
import org.rec.planets.mercury.communication.bean.JobResult;

/**
 * master --> slave 发布任务并等待结果返回
 * @author rec
 *
 */
public interface FetchResultService {
	JobResult fetch(JobInfo jobInfo);
}
