package org.rec.planets.mercury.communication.bean.pack;

import java.util.List;

import org.rec.planets.mercury.communication.bean.snapshot.JobResultSnapshot;
import org.rec.planets.mercury.domain.AbstractBean;

public class ResultPack extends AbstractBean {
	private static final long serialVersionUID = 5637800421282056801L;
	private Short nodeId;
	private List<JobResultSnapshot> jobResultSnapshots;

	public Short getNodeId() {
		return nodeId;
	}

	public void setNodeId(Short nodeId) {
		this.nodeId = nodeId;
	}

	public List<JobResultSnapshot> getJobResultSnapshots() {
		return jobResultSnapshots;
	}

	public void setJobResultSnapshots(List<JobResultSnapshot> jobResultSnapshots) {
		this.jobResultSnapshots = jobResultSnapshots;
	}
}
