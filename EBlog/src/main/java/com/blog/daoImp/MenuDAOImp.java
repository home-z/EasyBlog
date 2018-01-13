package com.blog.daoImp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.MenuDAO;
import com.blog.model.SysMenu;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月29日 下午10:43:57
 * @description：菜单权限
 */
@Repository
public class MenuDAOImp implements MenuDAO {

	@Override
	public List<SysMenu> getMenuByRoleId(String roleId) {
		List<SysMenu> list = HibernateUtils.queryListParam(SysMenu.class,
				"select * from sys_menu m where m.ParentID is not null and m.id in (select menuid from sys_roleauth where roleid='" + roleId
						+ "') order by m.id");

		return list;
	}

	@Override
	public List<SysMenu> getMenuByUserCode(String userCode) {
		// 根据用户编码，获取用户权限。先查用户角色，再查角色权限，再查菜单
		List<SysMenu> menus = HibernateUtils.queryListParam(SysMenu.class,
				"select * from sys_menu m where m.ParentID is not null and m.id in (select distinct menuid from sys_roleauth where roleid in (select RoleId from sys_userrole where usercode='"
						+ userCode + "')) order by m.id");

		return menus;
	}

	@Override
	public List<SysMenu> getAllMenus() {
		List<SysMenu> list = HibernateUtils.queryListParam(SysMenu.class,
				"select * from sys_menu m where m.ParentID is not null and m.id in (select menuid from sys_roleauth) order by m.id");

		return list;
	}

}
