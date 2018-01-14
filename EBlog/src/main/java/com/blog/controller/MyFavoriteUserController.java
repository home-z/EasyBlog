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

import com.blog.model.BllFavuser;
import com.blog.model.SysUsers;
import com.blog.service.MyFavoriteUserService;
import com.blog.utils.CoreConsts;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;

@Controller
@RequestMapping("/FavoriteUser")
public class MyFavoriteUserController {

	@Autowired
	private MyFavoriteUserService myFavoriteUserService;

	@RequestMapping("/getMyFavoriteUser")
	@ResponseBody
	public Map<String, Object> getMyFavoriteUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<BllFavuser> list = myFavoriteUserService.getMyFavoriteUser(currentUser.getUserCode());

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/addMyFavoriteUser")
	@ResponseBody
	public Map<String, String> addMyFavoriteUser(HttpServletResponse response, HttpServletRequest request) {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);

		BllFavuser myFavoriteUser = new BllFavuser();
		myFavoriteUser.setId(UUID.randomUUID().toString());
		myFavoriteUser.setUser(currentUser.getUserCode());
		myFavoriteUser.setFavUser(request.getParameter("favUser"));
		myFavoriteUser.setDescrible(request.getParameter("favUserDescrible"));

		myFavoriteUserService.addMyFavoriteUser(myFavoriteUser);

		return JsonHelper.getSucessResult(true, "保存成功！");
	}

	@RequestMapping("/updateMyFavoriteUser")
	@ResponseBody
	public Map<String, String> updateMyFavoriteUser(HttpServletResponse response, HttpServletRequest request) {
		BllFavuser myFavoriteUser = (BllFavuser) HibernateUtils.findById(BllFavuser.class,
				request.getParameter("favuserId"));

		myFavoriteUser.setFavUser(request.getParameter("favUser"));
		myFavoriteUser.setDescrible(request.getParameter("favUserDescrible"));

		myFavoriteUserService.updateMyFavoriteUser(myFavoriteUser);

		return JsonHelper.getSucessResult(true, "保存成功！");
	}

	@RequestMapping("/deleteMyFavoriteUser")
	@ResponseBody
	public Map<String, String> deleteMyFavoriteUser(HttpServletResponse response, HttpServletRequest request) {
		String favUserIds = request.getParameter("favUserIds");

		boolean result = myFavoriteUserService.deleteMyFavoriteUser(favUserIds);
		return JsonHelper.getSucessResult(result);
	}
}
