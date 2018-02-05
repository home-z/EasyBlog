package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author：Tim
 * @date：2018年2月5日 下午3:18:42
 * @description：用于首页（即不需要登录）跳转
 */

@Controller
@RequestMapping("/indexRoute")
public class IndexRouteController {

	/**
	 * 开始页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("/register")
	public String register() {
		return "admin/register";
	}
}
