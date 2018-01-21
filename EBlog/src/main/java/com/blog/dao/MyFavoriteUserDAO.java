package com.blog.dao;

import java.util.List;

import com.blog.po.BllFavuser;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:07:17
 * @description：TODO
 */
public interface MyFavoriteUserDAO {

	/**
	 * 获取当前用户关注的用户
	 * @param userCode 当前用户编码
	 * @return
	 */
	List<BllFavuser> getMyFavoriteUser(String userCode);

	boolean addMyFavoriteUser(BllFavuser myFavoriteUser);

	boolean updateMyFavoriteUser(BllFavuser myFavoriteUser);

	boolean deleteMyFavoriteUser(String favUserIds);
}
