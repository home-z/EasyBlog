package com.blog.model;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:04:34
 * @description：TODO
 */
public class SysRole implements java.io.Serializable {
	private int id;
	private String roleName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
