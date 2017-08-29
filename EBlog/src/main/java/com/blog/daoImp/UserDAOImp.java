package com.blog.daoImp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.dao.UserDAO;
import com.blog.model.SysUsers;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;
import com.blog.utils.MD5;
import com.blog.vo.UserSearchParams;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:12:20
 * @description：TODO
 */
@Repository
public class UserDAOImp implements UserDAO {

	@Override
	public boolean isUserCodeExist(String userCode) {
		int count = HibernateUtils.queryOne("select count(*) from sys_users where usercode='" + userCode + "'");

		return count == 0 ? false : true;
	}

	@Override
	public SysUsers login(String userCode, String userPassWord) {
		List<SysUsers> userList = HibernateUtils.queryListParam(SysUsers.class,
				"select * from sys_users where usercode=? and UserPassword=?", userCode, MD5.encode(userPassWord));

		if (userList.size() != 0) {
			return userList.get(0);// 用户是唯一的，查询出来是集合，其实只有一个用户，取第一个即可
		} else {
			return null;
		}
	}

	@Override
	public List<SysUsers> getUserList() {
		List<SysUsers> userList = HibernateUtils.queryListParam(SysUsers.class,
				"select * from sys_users order by createtime desc");

		return userList;
	}

	@Override
	public List<SysUsers> getUserCodeNotCurrent(String currentUser) {
		List<SysUsers> userList = HibernateUtils.queryListParam(SysUsers.class,
				"select * from sys_users where usercode!='" + currentUser + "'");

		return userList;
	}

	@Override
	public boolean addUser(SysUsers user) {
		user.setId(UUID.randomUUID().toString());// 生成一个id
		user.setUserPassword(MD5.encode(user.getUserPassword()));// 将密码加密后存入

		return HibernateUtils.add(user);
	}

	@Override
	public boolean updateUser(SysUsers user) {
		HibernateUtils.update(user);

		return true;
	}

	@Override
	public boolean deleteUser(String userId) {
		String[] deleteUserId = userId.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from sys_users where id in (");

		for (int i = 0; i < deleteUserId.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(deleteUserId[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}
		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");

		return HibernateUtils.executeSql(strSqlBlder.toString());
	}

	@Override
	public List<SysUsers> getUserHasPhotoList() {
		return HibernateUtils.queryListParam(SysUsers.class,
				"select * from sys_users where PhotoFingerPrint is not null and PhotoFingerPrint!=''");
	}

	@Override
	public List<SysUsers> getUserListByUserId(String userId) {
		String[] userIds = userId.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("select * from sys_users where id in (");

		for (int i = 0; i < userIds.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(userIds[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}
		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");

		return HibernateUtils.queryListParam(SysUsers.class, strSqlBlder.toString());
	}

	@Override
	public List<SysUsers> searchUser(UserSearchParams userSearchParams) {
		List<SysUsers> lstSysUsers = new ArrayList<SysUsers>();

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("select * from sys_users u where 1=1");
		if (userSearchParams.getUserCode() != null && !userSearchParams.getUserCode().equals("")) {
			strBuilder.append(" and u.UserCode like '%");
			strBuilder.append(userSearchParams.getUserCode());
			strBuilder.append("%'");
		}
		if (userSearchParams.getUserName() != null && !userSearchParams.getUserName().equals("")) {
			strBuilder.append(" and u.UserName like '%");
			strBuilder.append(userSearchParams.getUserName());
			strBuilder.append("%'");
		}
		if (userSearchParams.getEmail() != null && !userSearchParams.getEmail().equals("")) {
			strBuilder.append(" and u.Email like '%");
			strBuilder.append(userSearchParams.getEmail());
			strBuilder.append("%'");
		}
		lstSysUsers = HibernateUtils.queryListParam(SysUsers.class, strBuilder.toString(), null);

		return lstSysUsers;
	}

}
