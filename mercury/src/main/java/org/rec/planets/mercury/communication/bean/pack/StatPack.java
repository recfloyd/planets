package org.rec.planets.mercury.communication.bean.pack;

import java.util.List;

import org.rec.planets.mercury.communication.bean.snapshot.CurrentJobSnapshot;
import org.rec.planets.mercury.communication.bean.snapshot.WebsiteSnapshot;
import org.rec.planets.mercury.domain.AbstractBean;

public class StatPack extends AbstractBean {
	private static final long serialVersionUID = 6007211730985940912L;
	private Short nodeId;
	private List<CurrentJobSnapshot> currentJobSnapshots;
	private List<WebsiteSnapshot> websiteSnapshots;

	public Short getNodeId() {
		return nodeId;
	}

	public void setNodeId(Short nodeId) {
		this.nodeId = nodeId;
	}

	public List<CurrentJobSnapshot> getCurrentJobSnapshots() {
		return currentJobSnapshots;
	}

	public void setCurrentJobSnapshots(
			List<CurrentJobSnapshot> currentJobSnapshots) {
		this.currentJobSnapshots = currentJobSnapshots;
	}

	public List<WebsiteSnapshot> getWebsiteSnapshots() {
		return websiteSnapshots;
	}

	public void setWebsiteSnapshots(List<WebsiteSnapshot> websiteSnapshots) {
		this.websiteSnapshots = websiteSnapshots;
	}
}
