package com.blog.model;

import java.util.Date;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:04:34
 * @description：角色表
 */
public class SysRole implements java.io.Serializable {
	private String id;
	private String roleName;
	private Date createTime;
	private Date modifiedTime;
	private String creator;
	private String modifiedtor;
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifiedtor() {
		return modifiedtor;
	}

	public void setModifiedtor(String modifiedtor) {
		this.modifiedtor = modifiedtor;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
