package com.blog.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blog.po.BllArticle;
import com.blog.utils.DbAction;
import com.blog.utils.ElasticSearchUtils;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年5月4日 下午8:05:37
 * @description：博客搜索操作类，主要是导入已经有的数据库数据到ElasticSearch中
 */
public class BlogSearchService {

	// 将mysql中的博客导入到eulasticSearch中
	public static void dumpBlogToES() throws Exception {
		String strSql = "select * from bll_article";

		ResultSet rs = DbAction.getQuery(strSql);
		while (rs.next()) {
			Map<String, String> mapParam = new HashMap<String, String>();
			mapParam.put("id", rs.getString("id"));
			mapParam.put("title", rs.getString("title"));
			mapParam.put("content", rs.getString("content"));

			ElasticSearchUtils.addDoc("bll_article", rs.getString("id"), mapParam);
		}
	}

	public static void main(String[] args) throws SQLException {
		/*
		 * try { dumpBlogToES(); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

		// String strResult = ElasticSearchUtils.searchDoc("bll_article",
		// "content", "测试");
		// System.out.println(strResult);

		/*
		 * Map<String, String> shouldMap = new HashMap<String, String>();
		 * shouldMap.put("title", "北京"); shouldMap.put("content", "测试");
		 * 
		 * String strResult = ElasticSearchUtils.multiOrSearchDoc("bll_article",
		 * shouldMap); System.out.println(strResult);
		 */

		/*
		 * Map<String, String> mustMap = new HashMap<String, String>();
		 * mustMap.put("title", "北京"); mustMap.put("content", "北京");
		 * 
		 * String strResult =
		 * ElasticSearchUtils.multiAndSearchDoc("bll_article", mustMap);
		 * System.out.println(strResult);
		 */

		// String strResult =
		// ElasticSearchUtils.searchDocHighlight("bll_article", "content",
		// "测试");
		// System.out.println(strResult);

		// 测试title或者content中包含“测试”的内容
		Map<String, String> shouldMap = new HashMap<String, String>();
		shouldMap.put("title", "测试");
		shouldMap.put("content", "测试");

		Map<String, Object> mapResutl = ElasticSearchUtils.multiOrSearchDocHigh("bll_article", shouldMap, 0, 10);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) mapResutl.get("rows");
		for (Map<String, Object> mapRow : list) {
			String articleId = (String) mapRow.get("id");
			ResultSet rs = BlogService.getPostInfo(articleId);
			while (rs.next()) {
				System.out.println(rs.getString("CreateTime"));
				mapRow.put("createTime", rs.getString("CreateTime"));
				mapRow.put("readCount", rs.getString("ReadCount"));
				mapRow.put("suggestCount", rs.getString("SuggestCount"));
				mapRow.put("comCount", rs.getString("ComCount"));
			}
		}

	}

}
