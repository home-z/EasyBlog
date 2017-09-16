package com.blog.daoImp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.dao.RoleDAO;
import com.blog.model.SysRole;
import com.blog.utils.HibernateUtils;
import com.blog.vo.RoleSearchParams;

/**
 * @author：Tim
 * @date：2017年7月30日 下午10:43:01
 * @description：角色数据库操作实现类
 */
@Repository
public class RoleDAOImp implements RoleDAO {

	@Override
	public List<SysRole> searchRole(RoleSearchParams roleSearchParams) {
		List<SysRole> lstSysRoles = new ArrayList<SysRole>();

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("select * from sys_role r where 1=1");

		if (roleSearchParams.getRoleName() != null && !roleSearchParams.getRoleName().equals("")) {
			strBuilder.append(" and r.roleName like '%");
			strBuilder.append(roleSearchParams.getRoleName());
			strBuilder.append("%'");
		}

		strBuilder.append(" order by createtime desc ");
		lstSysRoles = HibernateUtils.queryListParam(SysRole.class, strBuilder.toString());

		return lstSysRoles;
	}

	@Override
	public boolean deleteRole(String roleId) {
		String[] deleteRoleId = roleId.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from sys_role where id in (");

		for (int i = 0; i < deleteRoleId.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(deleteRoleId[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}
		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");

		return HibernateUtils.executeSql(strSqlBlder.toString());
	}

	@Override
	public boolean isRoleNameExist(String roleName) {
		int count = HibernateUtils.queryOne("select count(*) from sys_role where roleName='" + roleName + "'");

		return count == 0 ? false : true;
	}

	@Override
	public boolean addRole(SysRole role) {
		role.setId(UUID.randomUUID().toString());// 生成一个id

		return HibernateUtils.add(role);
	}

	@Override
	public boolean updateRole(SysRole role) {
		HibernateUtils.update(role);

		return true;
	}

}
