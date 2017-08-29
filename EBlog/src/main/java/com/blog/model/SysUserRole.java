package com.blog.model;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:16:50
 * @description：TODO
 */
public class SysUserRole implements java.io.Serializable {
	private String id;
	private String userCode;
	private int roleId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
