package com.blog.model;

/**
 * @author：Tim
 * @date：2017年7月29日 下午10:12:03
 * @description：TODO
 */
public class SysMenu implements java.io.Serializable {

	private int id;
	private String menuName;
	private String uRL;
	private int parentID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getuRL() {
		return uRL;
	}

	public void setuRL(String uRL) {
		this.uRL = uRL;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

}
