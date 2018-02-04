package com.blog.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.utils.SessionHelper;
import com.blog.po.BllCommont;
import com.blog.service.CommentService;
import com.blog.utils.JsonHelper;

/**
 * @author：Tim
 * @date：2018年1月14日 下午4:20:35
 * @description：评论控制器
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

	private static Logger logger = Logger.getLogger(CommentController.class);

	@Autowired
	private CommentService commentService;

	@RequestMapping("/getCommentListByUser")
	@ResponseBody
	public Map<String, Object> getCommentListByUser() {
		List<BllCommont> list = commentService.getCommentListByUser(SessionHelper.getCurrentUserId(request));

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/deleteComment")
	@ResponseBody
	public Map<String, String> deleteComment(String commentIds) {
		boolean result = commentService.deleteComment(commentIds);

		logger.info("删除评论：" + commentIds);

		return JsonHelper.getSucessResult(result);
	}
}
