package org.rec.planets.mercury.communication.bean;

/**
 * 当前正在执行的任务
 * @author rec
 *
 */
public interface CurrentJob {
	Long getJobId();
	int getTotalSize();
	int getTodoSize();
}
