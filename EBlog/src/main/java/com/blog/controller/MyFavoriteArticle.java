package com.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
public class MyFavoriteArticle {

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
	public void addMyFavoriteArticle(HttpServletRequest request, HttpServletResponse response, BllFavarticle favArticle)
			throws IOException {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);

		favArticle.setId(UUID.randomUUID().toString());
		favArticle.setUser(currentUser.getUserCode());
		HibernateUtils.add(favArticle);

		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=utf-8");

		out.print("<script>alert('关注文章新增成功！');history.back();</script>");
		out.close();
	}
}
