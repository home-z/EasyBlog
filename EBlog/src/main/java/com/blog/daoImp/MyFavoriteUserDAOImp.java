package com.blog.daoImp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.MyFavoriteUserDAO;
import com.blog.model.BllFavuser;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:08:20
 * @description：TODO
 */
@Repository
public class MyFavoriteUserDAOImp implements MyFavoriteUserDAO {

	@Override
	public List<BllFavuser> getMyFavoriteUser(String userCode) {
		List<BllFavuser> list = HibernateUtils.queryListParam(BllFavuser.class,
				"select * from bll_favuser where user='" + userCode + "'");

		return list;
	}

}
