package org.rec.planets.mercury.communication.service;

import org.rec.planets.mercury.communication.bean.pack.JobPack;
import org.rec.planets.mercury.communication.bean.pack.PollPack;

/**
 * slave-->master 拉取任务
 * 
 * @author rec
 * 
 */
public interface PollJobService {
	JobPack poll(PollPack pollPack);
}
