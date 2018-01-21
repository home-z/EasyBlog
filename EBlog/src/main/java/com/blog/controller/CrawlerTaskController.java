package com.blog.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.po.BllCrawltask;
import com.blog.po.SysUser;
import com.blog.service.CrawlerTaskService;
import com.blog.utils.CoreConsts;
import com.blog.utils.JsonHelper;

/**
 * @author：Tim
 * @date：2018年1月15日 下午9:58:09
 * @description：TODO
 */
@Controller
@RequestMapping("/crawlerTask")
public class CrawlerTaskController {

	@Autowired
	private CrawlerTaskService crawlerTaskService;

	@RequestMapping("/getListCrawlerTaskByUser")
	@ResponseBody
	public Map<String, Object> getListCrawlerTaskByUser(HttpServletRequest request) {
		// 获取当前登录的用户
		SysUser currentUser = (SysUser) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<BllCrawltask> list = crawlerTaskService.getListCrawlerTaskByUser(currentUser.getUserCode());

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/removeCrawTask")
	@ResponseBody
	public Map<String, String> removeCrawTask(HttpServletResponse response, HttpServletRequest request) {
		String toDeleteIds = request.getParameter("deleteIds");

		boolean result = crawlerTaskService.removeCrawTask(toDeleteIds);
		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/addCrawlerTask")
	@ResponseBody
	public Map<String, String> addCrawlerTask(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取当前登录的用户
		SysUser currentUser = (SysUser) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);

		BllCrawltask crawltask = new BllCrawltask();

		crawltask.setId(UUID.randomUUID().toString());
		crawltask.setCreator(currentUser.getId());
		crawltask.setCrawlUrl(request.getParameter("crawlUrl"));
		crawltask.setKeyWords(request.getParameter("keyWords"));
		crawltask.setState(0);

		crawlerTaskService.addCrawlerTask(crawltask);

		return JsonHelper.getSucessResult(true, "保存成功！");
	}

}
