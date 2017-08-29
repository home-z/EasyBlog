package com.blog.dao;

import java.util.List;

import com.blog.model.SysUsers;
import com.blog.vo.UserSearchParams;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:10:23
 * @description：TODO
 */
public interface UserDAO {

	/**
	 * 根据条件搜索用户
	 * @param userSearchParams 搜索用户参数
	 * @return
	 */
	public List<SysUsers> searchUser(UserSearchParams userSearchParams);

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
	public SysUsers login(String userCode, String userPassWord);

	/**
	 * 选出所有用户
	 * @return
	 */
	public List<SysUsers> getUserList();

	/**
	 * 根据用户id选出所有用户
	 * @param userId 用户id
	 * @return
	 */
	public List<SysUsers> getUserListByUserId(String userId);

	/**
	 * 获取非当前用户的其他用户信息
	 * @param currentUser
	 * @return
	 */
	public List<SysUsers> getUserCodeNotCurrent(String currentUser);

	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	public boolean addUser(SysUsers user);

	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public boolean updateUser(SysUsers user);

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
	public List<SysUsers> getUserHasPhotoList();
}
