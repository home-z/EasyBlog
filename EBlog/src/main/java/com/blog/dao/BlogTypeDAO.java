package com.blog.dao;

import java.util.List;

import com.blog.model.BllArticletype;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:01:04
 * @description：博客类别
 */
public interface BlogTypeDAO {

	/**
	 * 根据博客类别id，获取博客类别对象
	 * @param blogTypeId 博客类别id
	 * @return
	 */
	public BllArticletype getBlogTypeById(String blogTypeId);

	/**
	 * 查询某用户的所有的博客类别
	 * @param userId 用户id
	 * @return
	 */
	public List<BllArticletype> getTypeListByUser(String userId);

	/**
	 * 删除博客类别
	 * @param blogTypeIds 博客类别id集合
	 * @return
	 */
	public boolean deleteBlogType(String blogTypeIds);

	/**
	 * 获取博客类别下博客数量
	 * @param blogTypeId 博客id
	 * @return
	 */
	public int getBlogCountByType(String blogTypeId);

	/**
	 * 新增博客类别
	 * @param articletype 博客类别对象
	 * @return
	 */
	public boolean addBlogType(BllArticletype articletype);

	/**
	 * 修改博客类别
	 * @param articletype 博客类别
	 * @return
	 */
	public boolean updateBlogType(BllArticletype articletype);

}
