package org.rec.planets.jupiter.communication.service;

import org.rec.planets.mercury.communication.bean.JobInfo;
import org.rec.planets.mercury.communication.bean.NodeInfo;
import org.rec.planets.mercury.communication.service.PollJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PollJobServiceImpl implements PollJobService {
	private static final Logger logger = LoggerFactory
			.getLogger(PollJobServiceImpl.class);

	@Override
	public JobInfo poll(NodeInfo nodeInfo) {
		// TODO
		// 此方法需要调用master提供的服务,服务可以用多种方式实现,如rmi,hessian等.如果master和slave部署在一个应用里,那么这里直接包装另一个实现类即可
		return null;
	}

}
