package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.CommentDAO;
import com.blog.model.BllCommont;

/**
 * @author：Tim
 * @date：2018年1月14日 下午4:23:59
 * @description：TODO
 */
@Service
public class CommentService {

	@Autowired
	private CommentDAO commentDAO;

	public List<BllCommont> getCommentListByUser(String userId) {
		return commentDAO.getCommentListByUser(userId);
	}

	public boolean deleteComment(String toDeleteIds) {
		return commentDAO.deleteComment(toDeleteIds);
	}
}
