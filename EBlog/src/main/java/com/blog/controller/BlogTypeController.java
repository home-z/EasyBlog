package com.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.BllArticletype;
import com.blog.model.SysUsers;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.utils.CoreConsts;

@Controller
@RequestMapping("/BlogType")
public class BlogTypeController {

	/**
	 * 获取用户下的博客分类
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getBlogTypeByUser")
	@ResponseBody
	public void getBlogTypeByUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<BllArticletype> list = HibernateUtils.queryListParam(BllArticletype.class,
				"select * from bll_articletype where userid='" + currentUser.getId() + "'");

		// 拼接Json字符串
		PrintWriter out = response.getWriter();
		StringBuffer strOut = new StringBuffer();

		strOut.append("[");

		for (BllArticletype blogtype : list) {
			strOut.append("{");
			strOut.append("\"id\":\"" + blogtype.getId() + "\",");
			strOut.append("\"text\":\"" + blogtype.getTypeName().toString() + "\"");
			strOut.append("},");
		}

		String strJsonString = strOut.substring(0, strOut.length() - 1);
		strJsonString += "]";

		out.println(strJsonString);
		out.close();
	}

	@RequestMapping("/getBlogTypeListByUser")
	@ResponseBody
	public Map<String, Object> getBlogTypeListByUser(HttpServletRequest request) {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<BllArticletype> list = HibernateUtils.queryListParam(BllArticletype.class,
				"select * from bll_articletype where userid='" + currentUser.getId() + "'");

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/addBlogType")
	@ResponseBody
	public Map<String, String> addBlogType(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String getnewRowsJson = new String(request.getParameter("newRowsJson").getBytes("ISO-8859-1"), "UTF-8");

		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);

		List newBlogTypelst = JsonHelper.getListFromJsonArrStr(getnewRowsJson, BllArticletype.class);

		for (int i = 0; i < newBlogTypelst.size(); i++) {
			BllArticletype articletype = (BllArticletype) newBlogTypelst.get(i);
			articletype.setId(UUID.randomUUID().toString());
			articletype.setUserId(currentUser.getId());

			HibernateUtils.add(articletype);
		}

		return JsonHelper.getSucessResult(true);
	}

	@RequestMapping("/updateBlogType")
	@ResponseBody
	public Map<String, String> updateBlogType(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String getupdateRowJson = new String(request.getParameter("updateRowJson").getBytes("ISO-8859-1"), "UTF-8");
		List updateBlogTypelst = JsonHelper.getListFromJsonArrStr(getupdateRowJson, BllArticletype.class);

		for (int i = 0; i < updateBlogTypelst.size(); i++) {
			BllArticletype articletype = (BllArticletype) updateBlogTypelst.get(i);

			HibernateUtils.update(articletype);
		}

		return JsonHelper.getSucessResult(true);
	}

	@RequestMapping("/deleteBlogType")
	@ResponseBody
	public Map<String, String> deleteBlogType(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String getdeleteRowsJson = new String(request.getParameter("deleteRowsJson").getBytes("ISO-8859-1"), "UTF-8");
		List deleteBlogTypelst = JsonHelper.getListFromJsonArrStr(getdeleteRowsJson, BllArticletype.class);

		for (int i = 0; i < deleteBlogTypelst.size(); i++) {
			BllArticletype articletype = (BllArticletype) deleteBlogTypelst.get(i);

			HibernateUtils.delete(articletype);
		}

		return JsonHelper.getSucessResult(true);
	}
}
