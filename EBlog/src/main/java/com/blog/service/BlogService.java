package com.blog.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.BlogDAO;
import com.blog.model.BllArticle;
import com.blog.model.BllCommont;
import com.blog.utils.DbAction;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;

/*操作Blog类
 */
@Service
public class BlogService {

	@Autowired
	private BlogDAO blogDAO;

	/**
	 * 查询文章
	 * 
	 * @throws ParseException
	 */
	public List<BllArticle> searchBlog(String vblogType, String vTitle, String vstartDate, String vendDate,
			String vContent, String currentUserCode) {
		return blogDAO.searchBlog(vblogType, vTitle, vstartDate, vendDate, vContent, currentUserCode);
	}

	/**
	 * 根据文章id，获取文章发布信息 TODO，这是给es搜索用的，待优化
	 * 
	 * @param articleID 文章id
	 * @return
	 */
	public static ResultSet getPostInfo(String articleID) {
		String strSql = "select CreateBy,CreateTime,ComCount,ReadCount,SuggestCount from bll_article  where id='"
				+ articleID + "'";

		ResultSet rs = DbAction.getQuery(strSql);
		return rs;
	}

	/**
	 * 报表统计
	 * 
	 * @param chartid
	 * @param styleType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ResultSet getBlogStatistics(String styleType, String startDate, String endDate) {
		return blogDAO.getBlogStatistics(styleType, startDate, endDate);
	}

	/**
	 * 读取该文章的评论
	 * @param articleID 文章id
	 * @return
	 */
	public List<BllCommont> getDetailById(String articleID) {
		return blogDAO.getDetailById(articleID);
	}
}
