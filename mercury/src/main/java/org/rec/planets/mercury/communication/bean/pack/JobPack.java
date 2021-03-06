package org.rec.planets.mercury.communication.bean.pack;

import java.util.List;
import java.util.Map;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.Job;

/**
 * 任务包
 * 
 * @author rec
 * 
 */
public class JobPack extends AbstractBean {
	private static final long serialVersionUID = 8969586934109142394L;
	private List<Job> jobs;// 任务
	private Map<String, Object> command;// 命令

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Map<String, Object> getCommand() {
		return command;
	}

	public void setCommand(Map<String, Object> command) {
		this.command = command;
	}
}
