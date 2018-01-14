package com.blog.dao;

import java.util.List;

import com.blog.model.BllArticletype;
import com.blog.model.BllFavarticle;

/**
 * @author：Tim
 * @date：2017年7月29日 下午9:04:58
 * @description：TODO
 */
public interface MyFavoriteArticleDAO {
	/**
	 * 读取当前用户关注的文章
	 * @param userCode 当前用户编码
	 * @return
	 */
	List<BllFavarticle> getMyFavoriteArticle(String userCode);

	boolean addMyFavoriteArticle(BllFavarticle favArticle);

	boolean updateMyFavoriteArticle(BllFavarticle favArticle);

	boolean deleteMyFavoriteArticle(String favArticleIdIds);
}
