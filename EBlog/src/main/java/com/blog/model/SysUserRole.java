package com.blog.model;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:16:50
 * @description：TODO
 */
public class SysUserRole implements java.io.Serializable {
	private String id;
	private String userCode;
	private String roleId;

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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
