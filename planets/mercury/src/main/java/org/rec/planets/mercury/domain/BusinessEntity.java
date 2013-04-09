package org.rec.planets.mercury.domain;

import java.util.Date;

import org.rec.planets.mercury.domain.constant.EntityStatus;

/**
 * 抽象实体
 * 
 * @author rec
 * 
 */
public abstract class BusinessEntity extends AbstractBean {
	private static final long serialVersionUID = 5843565863958301299L;
	/**
	 * 状态
	 */
	protected EntityStatus entityStatus;
	/**
	 * 创建者
	 */
	protected String createBy;
	/**
	 * 创建时间
	 */
	protected Date createTime;
	/**
	 * 更新者
	 */
	protected String updateBy;
	/**
	 * 更新时间
	 */
	protected Date updateTime;

	public EntityStatus getEntityStatus() {
		return entityStatus;
	}

	public void setEntityStatus(EntityStatus entityStatus) {
		this.entityStatus = entityStatus;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
