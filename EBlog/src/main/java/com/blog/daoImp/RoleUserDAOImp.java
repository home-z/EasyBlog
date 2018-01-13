package com.blog.daoImp;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.dao.RoleUserDAO;
import com.blog.model.SysUserRole;
import com.blog.model.SysUsers;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年9月16日 下午6:04:00
 * @description：角色用户数据库操作类实现类
 */
@Repository
public class RoleUserDAOImp implements RoleUserDAO {

	@Override
	public List<SysUsers> getRoleUser(String roleId) {
		List<SysUsers> userList = HibernateUtils.queryListParam(SysUsers.class,
				"select u.* from sys_users u inner join sys_userrole l on u.usercode=l.usercode where l.roleid='"
						+ roleId + "'");

		return userList;
	}

	@Override
	public boolean addRoleUser(String roleId, String userCodes) {
		String[] userCodesArray = userCodes.split(",");

		for (int i = 0; i < userCodesArray.length; i++) {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setId(UUID.randomUUID().toString());
			sysUserRole.setRoleId(roleId);
			sysUserRole.setUserCode(userCodesArray[i]);

			HibernateUtils.add(sysUserRole);
		}

		return true;
	}

	@Override
	public boolean removeRoleUser(String roleId, String userCodes) {
		String[] userCodesArray = userCodes.split(",");

		for (int i = 0; i < userCodesArray.length; i++) {
			String strSql = "delete from sys_userrole where roleid='" + roleId + "' and usercode='" + userCodesArray[i]
					+ "'";

			HibernateUtils.executeSql(strSql);
		}

		return true;
	}

	@Override
	public boolean isExistRoleUser(String roleId, String userCode) {
		String strSql = " select count(*) from sys_userrole l where l.roleid='" + roleId + "' and usercode='" + userCode
				+ "'";

		int count = HibernateUtils.queryOne(strSql);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

}
