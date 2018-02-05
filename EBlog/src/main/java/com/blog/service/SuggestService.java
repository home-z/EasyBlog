package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.BlogDAO;
import com.blog.dao.SuggestDAO;
import com.blog.po.BllSuggest;

/**
 * @author：Tim
 * @date：2018年1月14日 下午8:50:49
 * @description：TODO
 */
@Service
public class SuggestService {

	@Autowired
	private SuggestDAO suggestDAO;

	@Autowired
	private BlogDAO blogDAO;

	public List<BllSuggest> getSuggestListByUser(String userId) {
		return suggestDAO.getSuggestListByUser(userId);
	}

	public boolean deleteSuggest(String toDeleteIds) {
		boolean reduceCount = blogDAO.reduceSuggestCount(toDeleteIds);
		boolean deleteRecord = suggestDAO.deleteSuggest(toDeleteIds);

		return reduceCount && deleteRecord ? true : false;
	}

	public boolean addSuggest(BllSuggest suggest) {
		// 增加记录
		boolean addRecord = suggestDAO.addSuggest(suggest);

		// 对应文章的推荐次数加1
		boolean addCount = blogDAO.addSuggestCount(suggest.getArticleId());

		return addRecord && addCount ? true : false;
	}

	public boolean isExistSuggest(String articleId, String creatorId) {
		return suggestDAO.isExistSuggest(articleId, creatorId);
	}
}
