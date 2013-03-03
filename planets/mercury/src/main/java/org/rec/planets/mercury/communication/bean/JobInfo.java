package org.rec.planets.mercury.communication.bean;

import java.io.Serializable;
import java.util.List;

import org.rec.planets.mercury.domain.AbstractBean;
import org.rec.planets.mercury.domain.Job;

/**
 * 响应信息
 * 
 * @author rec
 * 
 */
public class JobInfo extends AbstractBean implements Serializable {
	private static final long serialVersionUID = -2241341711546183203L;
	private List<Job> jobs;// 任务
	private String command;// 命令

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
}
