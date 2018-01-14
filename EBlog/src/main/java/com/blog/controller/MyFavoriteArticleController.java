package com.blog.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.BllFavarticle;
import com.blog.model.SysUsers;
import com.blog.service.MyFavoriteArticleService;
import com.blog.utils.CoreConsts;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;

@Controller
@RequestMapping("/FavoriteArticle")
public class MyFavoriteArticleController {

	@Autowired
	private MyFavoriteArticleService myFavoriteArticleService;

	@RequestMapping("/getMyFavoriteArticle")
	@ResponseBody
	public Map<String, Object> getMyFavoriteArticle(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<BllFavarticle> list = myFavoriteArticleService.getMyFavoriteArticle(currentUser.getUserCode());

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/addMyFavoriteArticle")
	@ResponseBody
	public Map<String, String> addMyFavoriteArticle(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);

		BllFavarticle favArticle = new BllFavarticle();

		favArticle.setId(UUID.randomUUID().toString());
		favArticle.setUser(currentUser.getUserCode());
		favArticle.setArticleTitle(request.getParameter("articleTitle"));
		favArticle.setArticleUrl(request.getParameter("articleUrl"));
		favArticle.setDescrible(request.getParameter("describle"));

		myFavoriteArticleService.addMyFavoriteArticle(favArticle);

		return JsonHelper.getSucessResult(true, "保存成功！");
	}

	@RequestMapping("/updateMyFavoriteArticle")
	@ResponseBody
	public Map<String, String> updateMyFavoriteArticle(HttpServletResponse response, HttpServletRequest request) {
		BllFavarticle favArticle = (BllFavarticle) HibernateUtils.findById(BllFavarticle.class,
				request.getParameter("id"));
		favArticle.setArticleTitle(request.getParameter("articleTitle"));
		favArticle.setArticleUrl(request.getParameter("articleUrl"));
		favArticle.setDescrible(request.getParameter("describle"));

		myFavoriteArticleService.updateMyFavoriteArticle(favArticle);

		return JsonHelper.getSucessResult(true, "修改成功！");
	}

	@RequestMapping("/deleteMyFavoriteArticle")
	@ResponseBody
	public Map<String, String> deleteMyFavoriteArticle(HttpServletResponse response, HttpServletRequest request) {
		String favArticleIdIds = request.getParameter("favArticleIdIds");

		boolean result = myFavoriteArticleService.deleteMyFavoriteArticle(favArticleIdIds);
		return JsonHelper.getSucessResult(result);
	}
}
