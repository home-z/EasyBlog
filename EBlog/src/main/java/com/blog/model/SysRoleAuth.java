package com.blog.model;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:25:58
 * @description：TODO
 */
public class SysRoleAuth implements java.io.Serializable {
	private String id;
	private int roleId;
	private int menuId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

}
