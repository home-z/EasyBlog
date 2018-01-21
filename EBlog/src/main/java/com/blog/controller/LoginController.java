package com.blog.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.blog.vo.MenuTree;
import com.blog.constant.RuntimeEnvs;
import com.blog.po.SysUser;
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
		SysUser currentUser = (SysUser) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
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
		SysUser loginUser = userService.login(userCode, userPassWord);

		if (loginUser != null) {
			// 根据用户角色，获取用户的权限菜单
			List<MenuTree> menus = authService.getMenuTree(loginUser.getId());

			// 登录成功后会清除session，导致在首页选择的语言失效
			// TODO，不能记录？还是使用默认的英文
			Locale locale = (Locale) request.getSession()
					.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);

			// 登录成功，则设置全局用户信息，在其他页面检测是否经过登录
			HttpSession session = request.getSession();
			response.setHeader("Pragma", "No-cache");// 清理缓存
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			session.setAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER, loginUser);// 放入当前登录的用户
			session.setAttribute(CoreConsts.ExecuteContextKeys.CURRENT_MENU, menus);// 放入当前用户的菜单，登录后获取生成菜单
			session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);// session中记录当前语言，供springmvc读取切换语言

			// 系统变量赋值
			RuntimeEnvs.APP_ABSOLUTE_PATH = request.getServletContext().getRealPath("/");// 存储绝对路径
			RuntimeEnvs.SERVLETCONTEXT = request.getServletContext();// 存储执行环境
			RuntimeEnvs.CURRENT_USERCODE = loginUser.getUserCode();// 当前登录用户用户名
			RuntimeEnvs.CURRENT_USERID = loginUser.getId();// 当前登录用户ID

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

		// 变量清空
		RuntimeEnvs.APP_ABSOLUTE_PATH = null;// 存储绝对路径
		RuntimeEnvs.SERVLETCONTEXT = null;// 存储执行环境
		RuntimeEnvs.CURRENT_USERCODE = null;// 当前登录用户用户名
		RuntimeEnvs.CURRENT_USERID = null;

		return "admin/login";// 回到登录页
	}
}
