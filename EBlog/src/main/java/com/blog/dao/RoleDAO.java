package com.blog.dao;

import java.util.List;

import com.blog.model.SysRole;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:41:06
 * @description：TODO
 */
public interface RoleDAO {
	public List<SysRole> getUserRoles(String user);
}
