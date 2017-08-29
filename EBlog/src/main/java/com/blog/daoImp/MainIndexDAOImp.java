package com.blog.daoImp;

import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.blog.dao.MainIndexDAO;
import com.blog.model.BllArticle;
import com.blog.model.BllCommont;
import com.blog.utils.CoreConsts;
import com.blog.utils.DbAction;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月29日 下午8:22:40
 * @description：主页数据库操作类接口实现
 */
@Repository
public class MainIndexDAOImp implements MainIndexDAO {

	int pCount = CoreConsts.ExecuteContextKeys.PAGESIZE;// 每页显示记录数目

	@Override
	public int getArticlePage(String url) {
		String strSql = "select count(*) from bll_article ";

		// 判断是否是点击了分类，分类则带有类别参数，查询需要传入类别参数
		if (url.contains("typeid=")) {
			strSql = "select count(*) from bll_article where typeid=" + url.split("=")[1];
		}

		return (int) HibernateUtils.queryOne(strSql);// 总数
	}

	@Override
	public ResultSet getCategory() {
		String strSql = "select b.id typeid,b.typename,count(typeid) countn from bll_article a right join bll_articletype b on a.typeid=b.id group by b.id,b.typename order by countn desc";
		ResultSet rs = DbAction.getQuery(strSql);

		return rs;
	}

	@Override
	public List<BllArticle> getArticleByType(String typeid, String page) {
		int pageNum = 1;// 当前页
		if (!page.isEmpty() && page != "") {
			pageNum = Integer.parseInt(page);
		}
		String pageSql = " limit " + (pageNum - 1) * pCount + "," + pCount;

		List<BllArticle> articleList = HibernateUtils.queryListParam(BllArticle.class,
				"select * from bll_article where typeid=" + typeid + pageSql);

		return articleList;
	}

	@Override
	public List<BllArticle> getArticleBy(int byType, String page) {
		StringBuilder strSql = new StringBuilder();
		strSql.append("select * from bll_article order by ");
		switch (byType) {
			case 0:// 按照时间先后顺序排列
				strSql.append("CreateTime desc ");
				break;
			case 1:// 按照阅读量排列
				strSql.append("ReadCount desc ");
				break;
			case 2:// 按照评论量排列
				strSql.append("ComCount desc ");
			case 3:// 按照推荐量排列
				strSql.append("SuggestCount desc ");
				break;
		}

		int pageNum = 1;// 当前页
		if (!page.isEmpty() && page != "") {
			pageNum = Integer.parseInt(page);
		}
		strSql.append("limit ");
		strSql.append((pageNum - 1) * pCount);
		strSql.append(",");
		strSql.append(pCount);

		List<BllArticle> list = HibernateUtils.queryListParam(BllArticle.class, strSql.toString());

		return list;
	}

	@Override
	public int getSingleComm(String articleID) {
		int count = (int) HibernateUtils
				.queryOne("select count(*) from bll_commont where ArticleID='" + articleID + "'");

		return count;
	}

	@Override
	public List<BllArticle> getArticleByCreateBy(String user) {
		List<BllArticle> list = HibernateUtils.queryListParam(BllArticle.class,
				"select * from bll_article where createBy='" + user + "' order by CreateTime desc");

		return list;
	}

}
