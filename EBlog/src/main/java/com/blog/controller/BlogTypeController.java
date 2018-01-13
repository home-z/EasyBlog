package com.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.BllArticletype;
import com.blog.model.SysUsers;
import com.blog.service.BlogTypeService;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blog.utils.CoreConsts;

/**
 * 博客类别管理
 * @author tim
 * @date 2018年1月13日-下午6:19:38
 */
@Controller
@RequestMapping("/BlogType")
public class BlogTypeController {

	@Autowired
	private BlogTypeService blogTypeService;

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
		List<BllArticletype> typeList = blogTypeService.getTypeListByUser(currentUser.getId());

		// 拼接Json字符串
		PrintWriter out = response.getWriter();
		StringBuffer strOut = new StringBuffer();

		strOut.append("[");

		for (BllArticletype blogtype : typeList) {
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
		List<BllArticletype> typeList = blogTypeService.getTypeListByUser(currentUser.getId());

		return JsonHelper.getModelMapforGrid(typeList);
	}

	@RequestMapping("/addBlogType")
	@ResponseBody
	public Map<String, String> addBlogType(HttpServletResponse response, HttpServletRequest request) {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);

		BllArticletype articletype = new BllArticletype();
		articletype.setId(UUID.randomUUID().toString());
		articletype.setTypeName(request.getParameter("typeName"));
		articletype.setDescription(request.getParameter("description"));
		articletype.setUserId(currentUser.getId());

		blogTypeService.addBlogType(articletype);

		return JsonHelper.getSucessResult(true, "保存成功！");
	}

	@RequestMapping("/editBlogType")
	public String editBlogType(Model model, @RequestParam(value = "blogTypeId", required = true) String blogTypeId) {
		BllArticletype articletype = (BllArticletype) HibernateUtils.findById(BllArticletype.class, blogTypeId);
		model.addAttribute("blogTypeDTO", articletype);

		return "admin/blog/blogTypeEdit";
	}

	@RequestMapping("/updateBlogType")
	@ResponseBody
	public Map<String, String> updateBlogType(HttpServletResponse response, HttpServletRequest request) {
		BllArticletype articletype = (BllArticletype) HibernateUtils.findById(BllArticletype.class,
				request.getParameter("id"));
		articletype.setDescription(request.getParameter("description"));

		blogTypeService.updateBlogType(articletype);

		return JsonHelper.getSucessResult(true);
	}

	@RequestMapping("/deleteBlogType")
	@ResponseBody
	public Map<String, String> deleteBlogType(HttpServletResponse response, HttpServletRequest request) {
		String blogTypeIds = request.getParameter("blogTypeIds");

		String[] blogTypeIdArray = blogTypeIds.split(",");
		for (int i = 0; i < blogTypeIdArray.length; i++) {
			if (blogTypeService.getBlogCountByType(blogTypeIdArray[i]) > 0) {
				BllArticletype articletype = blogTypeService.getBlogTypeById(blogTypeIdArray[i]);
				return JsonHelper.getSucessResult(false, articletype.getTypeName() + "下存在博客，不能删除！");
			}
		}

		boolean result = blogTypeService.deleteBlogType(blogTypeIds);
		return JsonHelper.getSucessResult(result);
	}
}
