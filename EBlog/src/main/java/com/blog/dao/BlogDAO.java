package com.blog.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.blog.model.BllArticle;
import com.blog.model.BllCommont;

/**
 * @author：Tim
 * @date：2017年7月29日 下午8:51:19
 * @description：TODO
 */
public interface BlogDAO {

	/**
	 * 查询文章
	 * 
	 * @throws ParseException
	 */
	public List<BllArticle> searchBlog(String vblogType, String vTitle, String vstartDate, String vendDate,
			String vContent, String currentUserCode);

	/**
	 * 报表统计
	 * 
	 * @param chartid
	 * @param styleType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ResultSet getBlogStatistics(String styleType, String startDate, String endDate);

	/**
	 * 读取该文章的评论
	 * @param articleID 文章id
	 * @return
	 */
	public List<BllCommont> getDetailById(String articleID);
}
