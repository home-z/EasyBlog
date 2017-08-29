package com.blog.service;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.BlogDAO;
import com.blog.dao.MainIndexDAO;
import com.blog.model.BllArticle;
import com.blog.model.BllCommont;
import com.blog.utils.CoreConsts;
import com.blog.utils.DbAction;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月8日 上午10:53:11
 * @description：首页服务类
 */
@Service
public class MainIndexService {

	@Autowired
	private MainIndexDAO mainIndexDAO;// 主页dao

	@Autowired
	private BlogDAO blogDAO;// 博客dao

	/**
	 * 返回生成分页控件需要的数据
	 * @param url 前端点击的url
	 * @return 总的行数
	 */
	public int getArticlePage(String url) {
		return mainIndexDAO.getArticlePage(url);
	}

	/**
	 * 获取文章分类，以及每个分类下的文章总数
	 * @return
	 */
	public ResultSet getCategory() {
		return mainIndexDAO.getCategory();
	}

	/**
	 * 按照文章分类读取该分类下的文章
	 * @param typeid 文章分类id
	 * @param page 页数
	 * @return
	 */
	public List<BllArticle> getArticleByType(String typeid, String page) {
		return mainIndexDAO.getArticleByType(typeid, page);
	}

	/**
	 * 对文章进行各种排序
	 * @param byType 排序条件
	 * @param page 页数
	 * @return
	 */
	public List<BllArticle> getArticleBy(int byType, String page) {
		return mainIndexDAO.getArticleBy(byType, page);
	}

	/**
	 * 读取某个文章的评论数
	 * @param articleID 文章id
	 * @return
	 */
	public int getSingleComm(String articleID) {
		return mainIndexDAO.getSingleComm(articleID);
	}

	/**
	 * 读取该文章的评论
	 * @param articleID 文章id
	 * @return
	 */
	public List<BllCommont> getDetailById(String articleID) {
		return blogDAO.getDetailById(articleID);
	}

	/**
	 * 读取该用户的所有博客
	 * @param user 用户code
	 * @return
	 */
	public List<BllArticle> getArticleByCreateBy(String user) {
		return mainIndexDAO.getArticleByCreateBy(user);
	}
}
