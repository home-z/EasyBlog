package com.blog.daoImp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.BlogTypeDAO;
import com.blog.model.BllArticletype;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:02:13
 * @description：TODO
 */
@Repository
public class BlogTypeDAOImp implements BlogTypeDAO {

	@Override
	public List<BllArticletype> getTypeListByUser(String userId) {
		List<BllArticletype> typeList = HibernateUtils.queryListParam(BllArticletype.class,
				"select * from bll_articletype where userid='" + userId + "'");

		return typeList;
	}

}
