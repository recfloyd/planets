package org.rec.planets.jupiter.communication.service;

import org.rec.planets.mercury.communication.bean.JobInfo;
import org.rec.planets.mercury.communication.bean.JobResult;
import org.rec.planets.mercury.communication.service.FetchResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 由slave提供的服务,供master调用
 * @author rec
 *
 */
public class FetchResultServiceImpl implements FetchResultService {
	private static final Logger logger = LoggerFactory
			.getLogger(FetchResultServiceImpl.class);
	@Override
	public JobResult fetch(JobInfo jobInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
