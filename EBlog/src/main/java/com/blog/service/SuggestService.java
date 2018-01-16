package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.SuggestDAO;
import com.blog.model.BllSuggest;

/**
 * @author：Tim
 * @date：2018年1月14日 下午8:50:49
 * @description：TODO
 */
@Service
public class SuggestService {
	
	@Autowired
	private SuggestDAO suggestDAO;

	public List<BllSuggest> getSuggestListByUser(String userId) {
		return suggestDAO.getSuggestListByUser(userId);
	}

	public boolean deleteSuggest(String toDeleteIds) {
		return suggestDAO.deleteSuggest(toDeleteIds);
	}
}
