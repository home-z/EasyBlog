package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.BlogTypeDAO;
import com.blog.model.BllArticletype;
import com.blog.utils.HibernateUtils;

/**
 * @author：Tim
 * @date：2017年7月9日 下午9:36:49
 * @description：博客类别服务类
 */
@Service
public class BlogTypeService {

	@Autowired
	private BlogTypeDAO blogTypeDAO;

	public List<BllArticletype> getTypeListByUser(String userId) {
		return blogTypeDAO.getTypeListByUser(userId);
	}
}
