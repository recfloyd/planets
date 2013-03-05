package org.rec.planets.mercury.communication.service;

import org.rec.planets.mercury.communication.bean.NodeInfo;
import org.rec.planets.mercury.communication.bean.JobInfo;

/**
 * slave-->master 拉取任务
 * 
 * @author rec
 * 
 */
public interface PollJobService {
	JobInfo poll(NodeInfo nodeInfo);
}
