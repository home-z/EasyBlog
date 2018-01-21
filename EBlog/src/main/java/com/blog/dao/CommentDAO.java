package com.blog.dao;

import java.util.List;

import com.blog.po.BllCommont;

/**
 * @author：Tim
 * @date：2018年1月14日 下午4:26:09
 * @description：TODO
 */
public interface CommentDAO {
	List<BllCommont> getCommentListByUser(String userId);

	boolean deleteComment(String toDeleteIds);
}
