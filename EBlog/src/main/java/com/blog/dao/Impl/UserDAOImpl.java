package com.blog.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.dao.UserDAO;
import com.blog.po.SysUser;
import com.blog.utils.HibernateUtils;
import com.blog.utils.MD5;
import com.blog.vo.UserSearchParams;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:12:20
 * @description：用户数据库操作实现类
 */
@Repository
public class UserDAOImpl implements UserDAO {

	@Override
	public boolean isUserCodeExist(String userCode) {
		int count = HibernateUtils.queryOne("select count(*) from sys_user where usercode='" + userCode + "'");

		return count == 0 ? false : true;
	}

	@Override
	public SysUser login(String userCode, String userPassWord) {
		List<SysUser> userList = HibernateUtils.queryListParam(SysUser.class,
				"select * from sys_user where usercode=? and UserPassword=?", userCode, MD5.encode(userPassWord));

		if (userList.size() != 0) {
			return userList.get(0);// 用户是唯一的，查询出来是集合，其实只有一个用户，取第一个即可
		} else {
			return null;
		}
	}

	@Override
	public List<SysUser> getUserList() {
		List<SysUser> userList = HibernateUtils.queryListParam(SysUser.class,
				"select * from sys_user order by createtime desc");

		return userList;
	}

	@Override
	public List<SysUser> getUserCodeNotCurrent(String currentUser) {
		List<SysUser> userList = HibernateUtils.queryListParam(SysUser.class,
				"select * from sys_user where usercode!='" + currentUser + "'");

		return userList;
	}

	@Override
	public boolean addUser(SysUser user) {
		user.setId(UUID.randomUUID().toString());// 生成一个id
		user.setUserPassword(MD5.encode(user.getUserPassword()));// 将密码加密后存入

		return HibernateUtils.add(user);
	}

	@Override
	public boolean updateUser(SysUser user) {
		HibernateUtils.update(user);

		return true;
	}

	@Override
	public boolean deleteUser(String userId) {
		String[] deleteUserId = userId.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from sys_user where id in (");

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
	public List<SysUser> getUserHasPhotoList() {
		return HibernateUtils.queryListParam(SysUser.class,
				"select * from sys_user where PhotoFingerPrint is not null and PhotoFingerPrint!=''");
	}

	@Override
	public List<SysUser> getUserListByUserId(String userId) {
		String[] userIds = userId.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("select * from sys_user where id in (");

		for (int i = 0; i < userIds.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(userIds[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}
		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");

		return HibernateUtils.queryListParam(SysUser.class, strSqlBlder.toString());
	}

	@Override
	public List<SysUser> searchUser(UserSearchParams userSearchParams) {
		List<SysUser> lstSysUsers = new ArrayList<SysUser>();

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("select * from sys_user u where 1=1");
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

		strBuilder.append(" order by createtime desc ");
		lstSysUsers = HibernateUtils.queryListParam(SysUser.class, strBuilder.toString());

		return lstSysUsers;
	}

	@Override
	public SysUser getUserById(String userId) {
		return (SysUser) HibernateUtils.findById(SysUser.class, userId);
	}

}
