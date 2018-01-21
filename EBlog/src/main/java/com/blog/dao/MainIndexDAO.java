package com.blog.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.annotation.Resource;

import com.blog.po.BllArticle;
import com.blog.po.BllCommont;

/**
 * @author：Tim
 * @date：2017年7月29日 下午8:12:17
 * @description：主页数据库操作类接口
 */
public interface MainIndexDAO {

	/**
	 * 返回生成分页控件需要的数据
	 * @param url 前端点击的url
	 * @return 总的行数
	 */
	public int getArticlePage(String url);

	/**
	 * 获取文章分类，以及每个分类下的文章总数
	 * @return
	 */
	public ResultSet getCategory();

	/**
	 * 按照文章分类读取该分类下的文章
	 * @param typeid 文章分类id
	 * @param page 页数
	 * @return
	 */
	public List<BllArticle> getArticleByType(String typeid, String page);

	/**
	 * 对文章进行各种排序
	 * @param byType 排序条件
	 * @param page 页数
	 * @return
	 */
	public List<BllArticle> getArticleBy(int byType, String page);

	/**
	 * 读取某个文章的评论数
	 * @param articleID 文章id
	 * @return
	 */
	public int getSingleComm(String articleID);

	/**
	 * 读取该用户的所有博客
	 * @param user 用户code
	 * @return
	 */
	public List<BllArticle> getArticleByCreateBy(String user);
}
