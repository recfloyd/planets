package org.rec.planets.mercury.communication.service;

import org.rec.planets.mercury.communication.bean.JobInfo;
import org.rec.planets.mercury.communication.bean.JobResult;

public interface FetchResultService {
	JobResult fetch(JobInfo jobInfo);
}
