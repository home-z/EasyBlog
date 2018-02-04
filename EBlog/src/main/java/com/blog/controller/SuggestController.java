package com.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.po.BllSuggest;
import com.blog.service.SuggestService;
import com.blog.utils.JsonHelper;
import com.blog.utils.SessionHelper;

/**
 * @author：Tim
 * @date：2018年1月14日 下午8:46:41
 * @description：推荐控制器
 */
@Controller
@RequestMapping("/suggest")
public class SuggestController extends BaseController {
	@Autowired
	private SuggestService suggestService;

	@RequestMapping("/getSuggestListByUser")
	@ResponseBody
	public Map<String, Object> getSuggestListByUser() {
		List<BllSuggest> list = suggestService.getSuggestListByUser(SessionHelper.getCurrentUserId(request));

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/deleteSuggest")
	@ResponseBody
	public Map<String, String> deleteSuggest(String suggestIds) {
		boolean result = suggestService.deleteSuggest(suggestIds);

		return JsonHelper.getSucessResult(result);
	}
}
