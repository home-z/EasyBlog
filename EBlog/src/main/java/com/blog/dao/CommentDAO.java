package com.blog.dao;

import java.util.List;

import com.blog.po.BllCommont;

/**
 * @author：Tim
 * @date：2018年1月14日 下午4:26:09
 * @description：TODO
 */
public interface CommentDAO {

	/**
	 * 获取用户的评论
	 * @param userId 用户id
	 * @return
	 */
	List<BllCommont> getCommentListByUser(String userId);

	/**
	 * 删除评论
	 * @param toDeleteIds 评论id集合
	 * @return
	 */
	boolean deleteComment(String toDeleteIds);

	/**
	 * 读取该文章的评论
	 * @param articleID 文章id
	 * @return
	 */
	List<BllCommont> getCommentById(String articleID);

}
