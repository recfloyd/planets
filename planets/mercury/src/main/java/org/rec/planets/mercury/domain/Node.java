package org.rec.planets.mercury.domain;

import org.rec.planets.mercury.domain.constant.BandwidthType;

/**
 * 节点
 * 
 * @author lijia
 * 
 */
public class Node extends BusinessEntity{
	private static final long serialVersionUID = 8835583580164714349L;
	private Short id;
	/**
	 * 组
	 */
	private Integer groupId;
	/**
	 * 节点名
	 */
	private String name;
	/**
	 * 地理位置
	 */
	private String location;
	/**
	 * ip
	 */
	private String ip;
	/**
	 * 带宽,单位是Kb/s
	 */
	private Integer bandwidth;
	/**
	 * 带宽类型
	 */
	private BandwidthType bandwidthType;

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public BandwidthType getBandwidthType() {
		return bandwidthType;
	}

	public void setBandwidthType(BandwidthType bandwidthType) {
		this.bandwidthType = bandwidthType;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}
