package com.blog.dao;

import java.util.List;

import com.blog.po.SysUser;
import com.blog.vo.UserSearchParams;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:10:23
 * @description：TODO
 */
public interface UserDAO {

	/**
	 * 根据用户id，获取用户信息
	 * @param userId 用户id
	 * @return
	 */
	public SysUser getUserById(String userId);

	/**
	 * 根据条件搜索用户
	 * @param userSearchParams 搜索用户参数
	 * @return
	 */
	public List<SysUser> searchUser(UserSearchParams userSearchParams);

	/**
	 * 根据用户编码判断用户是否存在
	 * @param userCode 用户编码
	 * @return
	 */
	public boolean isUserCodeExist(String userCode);

	/**
	 * 根据用户编码和用户密码，校验用户
	 * @param userCode 用户编码
	 * @param userPassWord 用户密码
	 * @return
	 */
	public SysUser login(String userCode, String userPassWord);

	/**
	 * 选出所有用户
	 * @return
	 */
	public List<SysUser> getUserList();

	/**
	 * 根据用户id选出所有用户
	 * @param userId 用户id
	 * @return
	 */
	public List<SysUser> getUserListByUserId(String userId);

	/**
	 * 获取非当前用户的其他用户信息
	 * @param currentUser
	 * @return
	 */
	public List<SysUser> getUserCodeNotCurrent(String currentUser);

	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	public boolean addUser(SysUser user);

	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public boolean updateUser(SysUser user);

	/**
	 * 根据id删除用户
	 * @param getdeleteRowsJson
	 * @return
	 */
	public boolean deleteUser(String userId);

	/**
	 * 选出所有有头像的用户
	 * @return
	 */
	public List<SysUser> getUserHasPhotoList();
}
