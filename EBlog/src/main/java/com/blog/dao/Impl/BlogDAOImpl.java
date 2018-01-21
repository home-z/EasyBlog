package com.blog.dao.Impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.blog.dao.BlogDAO;
import com.blog.po.BllArticle;
import com.blog.po.BllCommont;
import com.blog.utils.DbAction;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月29日 下午8:55:33
 * @description：TODO
 */
@Repository
public class BlogDAOImpl implements BlogDAO {

	@Override
	public List<BllArticle> searchBlog(String vblogType, String vTitle, String vstartDate, String vendDate,
			String vContent, String currentUserCode) {
		List<BllArticle> lstBlogs = new ArrayList<BllArticle>();

		// 读取数据库
		StringBuilder strBulder = new StringBuilder();
		strBulder.append("SELECT * FROM bll_article b where ");

		if (vblogType != "") {
			strBulder.append(" b.TypeID = '");
			strBulder.append(vblogType);
			strBulder.append("' and ");
		}

		if (vTitle != "") {
			strBulder.append(" b.Title like '%");
			strBulder.append(vTitle);
			strBulder.append("%' and ");
		}
		if (vstartDate != "") {
			strBulder.append(" b.CreateTime >= date_format('");
			strBulder.append(vstartDate);
			strBulder.append(" 00:00:00"); // 提供更加精确的时间查找
			strBulder.append("', '%Y-%m-%d %T') and ");
		}
		if (vendDate != "") {
			strBulder.append(" b.CreateTime <= date_format('");
			strBulder.append(vendDate);
			strBulder.append(" 23:59:59"); // 提供更加精确的时间查找
			strBulder.append("', '%Y-%m-%d %T') and ");
		}
		if (vContent != "") {
			strBulder.append(" b.Content like '%");
			strBulder.append(vContent);
			strBulder.append("%' and ");
		}

		// 按照用户查询
		strBulder.append(" CreateBy ='");
		strBulder.append(currentUserCode);
		strBulder.append("'");
		lstBlogs = HibernateUtils.queryListParam(BllArticle.class, strBulder.toString(), null);

		return lstBlogs;
	}

	@Override
	public ResultSet getBlogStatistics(String styleType, String startDate, String endDate) {
		String strSql = " select date_formatstyleType postDate,count(id) postCount from bll_article where createtime>= date_format('"
				+ startDate + " 00:00:00', '%Y-%m-%d %T') and createtime<= date_format('" + endDate
				+ " 23:59:59', '%Y-%m-%d %T') group by date_formatstyleType";
		switch (styleType) {
		case "0":// 按天统计
			strSql = strSql.replace("date_formatstyleType", "date_format(createtime,'%Y-%m-%d')");
			break;
		case "1":// 按月统计
			strSql = strSql.replace("date_formatstyleType", "date_format(createtime,'%Y-%m')");
			break;
		case "2":// 按年统计
			strSql = strSql.replace("date_formatstyleType", "date_format(createtime,'%Y')");
			break;
		default:
			strSql = strSql.replace("date_formatstyleType", "date_format(createtime,'%Y-%m-%d')");
			break;
		}

		ResultSet rs = DbAction.getQuery(strSql);

		return rs;
	}

	@Override
	public List<BllCommont> getDetailById(String articleID) {
		// 读取该文章的评论
		List<BllCommont> comList = HibernateUtils.queryListParam(BllCommont.class,
				"select * from bll_commont where ArticleID='" + articleID + "' order by comtime asc");

		return comList;
	}

	@Override
	public int getCountByUserCode(String userCode) {
		String strSql = "select count(*) from bll_article where createby='" + userCode + "'";

		return HibernateUtils.queryOne(strSql);
	}

	@Override
	public int getCountByUserId(String userId) {
		String strSql = "select count(*) from bll_article b inner join sys_users u on b.createby=u.usercode where u.id='"
				+ userId + "'";

		return HibernateUtils.queryOne(strSql);
	}

}
