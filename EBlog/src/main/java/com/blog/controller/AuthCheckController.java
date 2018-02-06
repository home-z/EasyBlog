package com.blog.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author：Tim
 * @date：2018年2月5日 下午10:40:07 
 * @description：登录检查。需要用户登录后才能操作的类继承该父类。
 */

public class AuthCheckController extends BaseController {

	@ModelAttribute
	protected void checkHasLogin() {

	}

}
