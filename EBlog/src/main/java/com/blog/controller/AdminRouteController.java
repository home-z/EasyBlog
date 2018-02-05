package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author：Tim
 * @date：2018年2月5日 下午10:19:22
 * @description：用于需要登录后的页面跳转，需要进行权限检查
 */

@Controller
@RequestMapping("/adminRoute")
public class AdminRouteController {
	
	@RequestMapping("/admin")
	public String index() {
		return "admin/admin";
	}
}
