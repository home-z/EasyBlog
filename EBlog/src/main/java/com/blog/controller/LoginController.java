package com.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.SysUsers;
import com.blog.utils.CoreConsts;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;

@Controller
@RequestMapping("/Login")
public class LoginController {
	
	/**
	 * 判断用户名是否存在
	 * 
	 * @param usercode
	 *            用户用户名
	 * @return Map
	 */
	@RequestMapping("/isExist")
	@ResponseBody
	public Map<String, String> isUserCodeExist(@RequestParam(value = "usercode", required = true) String usercode) {
		SysUsers user = (SysUsers) HibernateUtils.findById(SysUsers.class, usercode);
		if (user != null) {
			return JsonHelper.getSucessResult(true);
		}
		return JsonHelper.getSucessResult(false);
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
	 * @param userCode
	 *            用户名
	 * @param userPassWord
	 *            用户密码
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "userCode", required = true) String userCode,
			@RequestParam(value = "userPassWord", required = true) String userPassWord) throws IOException {
		List<SysUsers> usertest = HibernateUtils.queryListParam(SysUsers.class,
				"select * from sys_users where usercode=? and UserPassword=?", userCode, userPassWord);
		if (usertest.size() != 0) {
			// 登录成功，设置全局用户信息，在其他页面检测是否经过登录
			SysUsers currentUser = usertest.get(0);

			HttpSession session = request.getSession();

			// 清理缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			// 放入当前登录的用户
			session.setAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER, currentUser);

			return "admin/admin";
		} else {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=utf-8");

			out.print("<script>alert('登录失败！');self.location=document.referrer;</script>");// 弹窗，返回上一页并刷新
			out.close();

			return "admin/login";
		}
	}
}
