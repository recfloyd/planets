package org.rec.planets.mercury.communication.bean.snapshot;

import org.rec.planets.mercury.domain.AbstractBean;

/**
 * 抽象快照
 * 
 * @author rec
 * 
 */
public class AbstractSnapshot extends AbstractBean {
	private static final long serialVersionUID = -8413122431848966750L;
	protected long snapshotStart;
	protected long snapshotEnd;

	public long getSnapshotStart() {
		return snapshotStart;
	}

	public void setSnapshotStart(long snapshotStart) {
		this.snapshotStart = snapshotStart;
	}

	public long getSnapshotEnd() {
		return snapshotEnd;
	}

	public void setSnapshotEnd(long snapshotEnd) {
		this.snapshotEnd = snapshotEnd;
	}

}
