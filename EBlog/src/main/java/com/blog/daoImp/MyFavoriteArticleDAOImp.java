package com.blog.daoImp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.MyFavoriteArticleDAO;
import com.blog.model.BllFavarticle;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:05:23
 * @description：TODO
 */
@Repository
public class MyFavoriteArticleDAOImp implements MyFavoriteArticleDAO {

	@Override
	public List<BllFavarticle> getMyFavoriteArticle(String userCode) {
		List<BllFavarticle> list = HibernateUtils.queryListParam(BllFavarticle.class,
				"select * from bll_favarticle where user='" + userCode + "'");

		return list;
	}

}
