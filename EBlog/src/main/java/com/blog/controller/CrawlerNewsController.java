package com.blog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.BllPageinfo;
import com.blog.model.SysUsers;
import com.blog.service.CrawlerNewsService;
import com.blog.utils.CoreConsts;
import com.blog.utils.JsonHelper;

/**
 * @author：Tim
 * @date：2018年1月16日 下午10:33:36
 * @description：TODO
 */
@Controller
@RequestMapping("/crawlerNews")
public class CrawlerNewsController {

	@Autowired
	private CrawlerNewsService CrawlerNewsService;

	@RequestMapping("/getListCrawlerNewsByUser")
	@ResponseBody
	public Map<String, Object> getListCrawlerNewsByUser(HttpServletRequest request) {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<BllPageinfo> list = CrawlerNewsService.getListCrawlerNewsByUser(currentUser.getUserCode());

		return JsonHelper.getModelMapforGrid(list);
	}
}
