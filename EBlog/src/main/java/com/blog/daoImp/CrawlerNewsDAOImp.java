package com.blog.daoImp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.CrawlerNewsDAO;
import com.blog.model.BllPageinfo;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2018年1月16日 下午10:37:35
 * @description：TODO
 */
@Repository
public class CrawlerNewsDAOImp implements CrawlerNewsDAO {

	@Override
	public List<BllPageinfo> getListCrawlerNewsByUser(String userId) {
		List<BllPageinfo> list = HibernateUtils.queryListParam(BllPageinfo.class,
				"select * from bll_pageinfo where CreateBy='" + userId + "'");

		return list;
	}

}
