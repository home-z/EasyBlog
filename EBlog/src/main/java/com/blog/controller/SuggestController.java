package com.blog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.BllSuggest;
import com.blog.model.SysUsers;
import com.blog.service.SuggestService;
import com.blog.utils.CoreConsts;
import com.blog.utils.JsonHelper;

/**
 * @author：Tim
 * @date：2018年1月14日 下午8:46:41
 * @description：推荐控制器
 */
@Controller
@RequestMapping("/suggest")
public class SuggestController {
	@Autowired
	private SuggestService suggestService;

	@RequestMapping("/getSuggestListByUser")
	@ResponseBody
	public Map<String, Object> getSuggestListByUser(HttpServletRequest request) {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<BllSuggest> list = suggestService.getSuggestListByUser(currentUser.getUserCode());

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/deleteSuggest")
	@ResponseBody
	public Map<String, String> deleteSuggest(HttpServletResponse response, HttpServletRequest request) {
		String toDeleteIds = request.getParameter("suggestIds");

		boolean result = suggestService.deleteSuggest(toDeleteIds);
		return JsonHelper.getSucessResult(result);
	}
}
