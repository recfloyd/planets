package org.rec.planets.mercury.domain;


/**
 * 节点组
 * 
 * @author lijia
 * 
 */
public class NodeGroup extends BusinessEntity {
	private static final long serialVersionUID = 5037406935792860698L;
	private Integer id;
	/**
	 * 位置
	 */
	private String location;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
