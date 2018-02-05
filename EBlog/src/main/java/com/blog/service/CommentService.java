package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.BlogDAO;
import com.blog.dao.CommentDAO;
import com.blog.po.BllCommont;
import com.blog.vo.CommentRequest;

/**
 * @author：Tim
 * @date：2018年1月14日 下午4:23:59
 * @description：TODO
 */
@Service
public class CommentService {

	@Autowired
	private CommentDAO commentDAO;

	@Autowired
	private BlogDAO blogDAO;

	public List<BllCommont> getCommentListByUser(String userId) {
		return commentDAO.getCommentListByUser(userId);
	}

	public boolean deleteComment(String toDeleteIds) {
		return commentDAO.deleteComment(toDeleteIds);
	}

	/**
	 * 读取该文章的评论
	 * @param articleID 文章id
	 * @return
	 */
	public List<BllCommont> getCommentById(String articleID) {
		return commentDAO.getCommentById(articleID);
	}

	public boolean addComment(BllCommont commont) {
		// 增加记录
		boolean addRecord = commentDAO.addComment(commont);

		// 该文章的评论次数加1
		boolean addCount = blogDAO.addComCount(commont.getArticleId());

		return addRecord && addCount ? true : false;
	}

	public List<CommentRequest> getCommentRequestById(String articleID) {
		return commentDAO.getCommentRequestById(articleID);
	}
}
