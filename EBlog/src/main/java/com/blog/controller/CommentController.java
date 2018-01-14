package com.blog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.BllArticletype;
import com.blog.model.BllCommont;
import com.blog.model.SysUsers;
import com.blog.service.CommentService;
import com.blog.utils.CoreConsts;
import com.blog.utils.JsonHelper;

/**
 * @author：Tim
 * @date：2018年1月14日 下午4:20:35
 * @description：TODO
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@RequestMapping("/getCommentListByUser")
	@ResponseBody
	public Map<String, Object> getCommentListByUser(HttpServletRequest request) {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<BllCommont> list = commentService.getCommentListByUser(currentUser.getUserCode());

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/deleteComment")
	@ResponseBody
	public Map<String, String> deleteComment(HttpServletResponse response, HttpServletRequest request) {
		String toDeleteIds = request.getParameter("commentIds");

		boolean result = commentService.deleteComment(toDeleteIds);
		return JsonHelper.getSucessResult(result);
	}
}
