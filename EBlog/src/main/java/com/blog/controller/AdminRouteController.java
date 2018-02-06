package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author：Tim
 * @date：2018年2月5日 下午10:19:22
 * @description：后台页面跳转。用于需要登录后的页面跳转，需要进行权限检查
 */

@Controller
@RequestMapping("/adminRoute")
public class AdminRouteController {

	/**
	 * 后台主页
	 * @return
	 */
	@RequestMapping("/admin")
	public String index() {
		return "admin/admin";
	}

	/**
	 * 欢迎页
	 * @return
	 */
	@RequestMapping("/welcome")
	public String welcomme() {
		return "admin/welcome";
	}

	/**
	 * 文章类别
	 * @return
	 */
	@RequestMapping("/articleType")
	public String articleType() {
		return "/admin/blog/blogtype";
	}

	/**
	 * 文章
	 * @return
	 */
	@RequestMapping("/article")
	public String article() {
		return "/admin/blog/blog";
	}

	/**
	 * 文章编辑新建
	 * @return
	 */
	@RequestMapping("/articleInfo")
	public String articleInfo() {
		return "/admin/blog/bloginfo";
	}

	/**
	 * 我的关注
	 * @return
	 */
	@RequestMapping("/myfavorite")
	public String myfavorite() {
		return "/admin/favorite/myfavorite";
	}

	/**
	 * 我的评论
	 * @return
	 */
	@RequestMapping("/mycomment")
	public String mycomment() {
		return "/admin/favorite/mycomment";
	}

	/**
	 * 我的推荐
	 * @return
	 */
	@RequestMapping("/mysuggests")
	public String mysuggests() {
		return "/admin/favorite/mysuggests";
	}

	/**
	 * 抓取设置
	 * @return
	 */
	@RequestMapping("/crawlsetting")
	public String crawlsetting() {
		return "/admin/crawler/crawlsetting";
	}

	/**
	 * 我的抓取
	 * @return
	 */
	@RequestMapping("/crawlnews")
	public String crawlnews() {
		return "/admin/crawler/crawlnews";
	}

	/**
	 * 用户管理
	 * @return
	 */
	@RequestMapping("/users")
	public String users() {
		return "/admin/system/users";
	}

	@RequestMapping("/roles")
	public String roles() {
		return "/admin/system/roles";
	}

	@RequestMapping("/userroles")
	public String userroles() {
		return "/admin/system/userroles";
	}

	@RequestMapping("/rolesauth")
	public String rolesauth() {
		return "/admin/system/rolesauth";
	}

}
