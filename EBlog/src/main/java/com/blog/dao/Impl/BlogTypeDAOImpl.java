package com.blog.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.BlogTypeDAO;
import com.blog.po.BllArticletype;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:02:13
 * @description：TODO
 */
@Repository
public class BlogTypeDAOImpl implements BlogTypeDAO {

	@Override
	public List<BllArticletype> getTypeListByUser(String userId) {
		List<BllArticletype> typeList = HibernateUtils.queryListParam(BllArticletype.class,
				"select * from bll_articletype where userid='" + userId + "'");

		return typeList;
	}

	@Override
	public boolean deleteBlogType(String blogTypeIds) {
		String[] deleteidArray = blogTypeIds.split(",");

		StringBuilder strSqlBlder = new StringBuilder();
		strSqlBlder.append("delete from bll_articletype where id in (");

		for (int i = 0; i < deleteidArray.length; i++) {
			strSqlBlder.append("'");
			strSqlBlder.append(deleteidArray[i]);
			strSqlBlder.append("'");
			strSqlBlder.append(",");
		}
		strSqlBlder.deleteCharAt(strSqlBlder.length() - 1);
		strSqlBlder.append(")");

		return HibernateUtils.executeSql(strSqlBlder.toString());
	}

	@Override
	public int getBlogCountByType(String blogTypeId) {
		String strSql = "select count(*) from bll_article where typeid='" + blogTypeId + "'";

		return HibernateUtils.queryOne(strSql);
	}

	@Override
	public BllArticletype getBlogTypeById(String blogTypeId) {
		return (BllArticletype) HibernateUtils.findById(BllArticletype.class, blogTypeId);
	}

	@Override
	public boolean addBlogType(BllArticletype articletype) {
		return HibernateUtils.add(articletype);
	}

	@Override
	public boolean updateBlogType(BllArticletype articletype) {
		return HibernateUtils.update(articletype);
	}

}
