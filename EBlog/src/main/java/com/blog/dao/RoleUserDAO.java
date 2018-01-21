package com.blog.dao;

import java.util.List;

import com.blog.po.SysUser;

/**
 * @author：Tim
 * @date：2017年9月16日 下午6:02:34
 * @description：角色用户数据库操作接口
 */
public interface RoleUserDAO {

	/**
	 * 根据角色id，得到角色下用户
	 * @param roleId 角色id
	 * @return
	 */
	public List<SysUser> getRoleUser(String roleId);

	/**
	 * 增加用户到角色下
	 * @param roleId 角色id
	 * @param userCodes 用户编码
	 * @return
	 */
	public boolean addRoleUser(String roleId, String userCodes);

	/**
	 * 从角色下移除用户
	 * @param roleId 角色id
	 * @param userCodes 用户编码
	 * @return
	 */
	public boolean removeRoleUser(String roleId, String userCodes);

	/**
	 * 判断角色下用户是否存在
	 * @param roleId 角色id
	 * @param usercode 用户编码
	 * @return
	 */
	public boolean isExistRoleUser(String roleId, String userCode);
}
