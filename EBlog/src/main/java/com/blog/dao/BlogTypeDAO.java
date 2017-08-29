package com.blog.dao;

import java.util.List;

import com.blog.model.BllArticletype;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:01:04
 * @description：TODO
 */
public interface BlogTypeDAO {

	/**
	 * 查询某用户的所有的类型
	 * @param userId
	 * @return
	 */
	public List<BllArticletype> getTypeListByUser(String userId);
}
