package com.blog.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.vo.MenuTree;
import com.blog.model.SysUsers;
import com.blog.service.AuthService;
import com.blog.service.UserService;
import com.blog.utils.CoreConsts;
import com.blog.utils.JsonHelper;

@Controller
@RequestMapping("/Login")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;// 权限

	/**
	 * 判断用户名是否存在
	 * 
	 * @param usercode 用户用户名
	 * @return Map
	 */
	@RequestMapping("/isExist")
	@ResponseBody
	public Map<String, String> isUserCodeExist(@RequestParam(value = "userCode", required = true) String userCode) {
		boolean isUserExist = userService.isUserCodeExist(userCode);

		return JsonHelper.getSucessResult(isUserExist);
	}

	/**
	 * 进入登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/loginpage", method = RequestMethod.GET)
	public String loginpage(HttpServletRequest request) {
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		if (currentUser != null) {
			// 已经登录的用户不能进入登录页面，直接进入管理页
			return "admin/admin";
		}

		return "admin/login";
	}

	/**
	 * 登录
	 * 
	 * @param userCode 用户名
	 * @param userPassWord 用户密码
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, String> login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "userCode", required = true) String userCode,
			@RequestParam(value = "userPassWord", required = true) String userPassWord) throws IOException {
		SysUsers loginUser = userService.login(userCode, userPassWord);

		if (loginUser != null) {
			// 根据用户角色，获取用户的权限菜单
			List<MenuTree> menus = authService.getMenuTree(loginUser.getUserCode());
			
			// 登录成功，则设置全局用户信息，在其他页面检测是否经过登录
			HttpSession session = request.getSession();
			response.setHeader("Pragma", "No-cache");// 清理缓存
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			session.setAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER, loginUser);// 放入当前登录的用户
			session.setAttribute(CoreConsts.ExecuteContextKeys.CURRENT_MENU, menus);// 放入当前用户的菜单，登录后获取生成菜单
			CoreConsts.Runtime.APP_ABSOLUTE_PATH = request.getServletContext().getRealPath("/");// 存储绝对路径
			CoreConsts.Runtime.SERVLETCONTEXT = request.getServletContext();// 存储执行环境

			return JsonHelper.getSucessResult(true);
		} else {
			return JsonHelper.getSucessResult(false);
		}
	}

	/**
	 * 用户退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();// 清除缓存

		return "admin/login";// 回到登录页
	}
}
