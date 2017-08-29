package com.blog.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.BllArticletype;
import com.blog.model.BllFavuser;
import com.blog.model.SysUsers;
import com.blog.service.MyFavoriteUserService;
import com.blog.utils.CoreConsts;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;

@Controller
@RequestMapping("/FavoriteUser")
public class MyFavoriteUser {

	@Autowired
	private MyFavoriteUserService myFavoriteArticleService;

	@RequestMapping("/getMyFavoriteUser")
	@ResponseBody
	public Map<String, Object> getMyFavoriteUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<BllFavuser> list = myFavoriteArticleService.getMyFavoriteUser(currentUser.getUserCode());

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/addMyFavoriteUser")
	@ResponseBody
	public Map<String, String> addMyFavoriteUser(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String getnewRowsJson = new String(request.getParameter("newRowsJson").getBytes("ISO-8859-1"), "UTF-8");

		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);

		List newaddMyFavoriteUserlst = JsonHelper.getListFromJsonArrStr(getnewRowsJson, BllFavuser.class);

		for (int i = 0; i < newaddMyFavoriteUserlst.size(); i++) {
			BllFavuser myFavoriteUser = (BllFavuser) newaddMyFavoriteUserlst.get(i);
			myFavoriteUser.setId(UUID.randomUUID().toString());
			myFavoriteUser.setUser(currentUser.getUserCode());

			HibernateUtils.add(myFavoriteUser);
		}

		return JsonHelper.getSucessResult(true);
	}

	@RequestMapping("/updateMyFavoriteUser")
	@ResponseBody
	public Map<String, String> updateMyFavoriteUser(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String getupdateRowJson = new String(request.getParameter("updateRowJson").getBytes("ISO-8859-1"), "UTF-8");
		List updateMyFavoriteUserlst = JsonHelper.getListFromJsonArrStr(getupdateRowJson, BllFavuser.class);

		for (int i = 0; i < updateMyFavoriteUserlst.size(); i++) {
			BllFavuser myFavoriteUser = (BllFavuser) updateMyFavoriteUserlst.get(i);

			HibernateUtils.update(myFavoriteUser);
		}

		return JsonHelper.getSucessResult(true);
	}

	@RequestMapping("/deleteMyFavoriteUser")
	@ResponseBody
	public Map<String, String> deleteMyFavoriteUser(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String getdeleteRowsJson = new String(request.getParameter("deleteRowsJson").getBytes("ISO-8859-1"), "UTF-8");
		List deleteMyFavoriteUserlst = JsonHelper.getListFromJsonArrStr(getdeleteRowsJson, BllFavuser.class);

		for (int i = 0; i < deleteMyFavoriteUserlst.size(); i++) {
			BllFavuser myfavuser = (BllFavuser) deleteMyFavoriteUserlst.get(i);

			HibernateUtils.delete(myfavuser);
		}

		return JsonHelper.getSucessResult(true);
	}
}
