package com.blog.dao;

import java.util.List;

import com.blog.model.SysMenu;

/**
 * @author：Tim
 * @date：2017年7月29日 下午10:43:16
 * @description：TODO
 */
public interface MenuDAO {

	public List<SysMenu> getMenuByRoleId(int roleId);

	/**
	 * 根据用户编码获取用户权限下的菜单
	 * @param userCode 用户编码
	 * @return 用户菜单
	 */
	public List<SysMenu> getMenuByUserCode(String userCode);
}
