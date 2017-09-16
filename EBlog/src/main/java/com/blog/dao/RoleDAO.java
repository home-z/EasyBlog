package com.blog.dao;

import java.util.List;

import com.blog.model.SysRole;
import com.blog.vo.RoleSearchParams;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:41:06
 * @description：TODO
 */
public interface RoleDAO {

	/**
	 * 查找角色
	 * @param roleSearchParams 查找角色参数
	 * @return
	 */
	public List<SysRole> searchRole(RoleSearchParams roleSearchParams);

	/**
	 * 删除角色
	 * @param roleId 角色id
	 * @return
	 */
	public boolean deleteRole(String roleId);

	/**
	 * 判断角色名称是否存在
	 * @param roleName 角色名称
	 * @return
	 */
	public boolean isRoleNameExist(String roleName);

	/**
	 * 新增角色
	 * @param role 角色对象
	 * @return
	 */
	public boolean addRole(SysRole role);

	/**
	 * 更新角色
	 * @param role 角色对象
	 * @return
	 */
	public boolean updateRole(SysRole role);
	
}
