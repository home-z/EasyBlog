package com.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.SysMenu;
import com.blog.service.AuthService;
import com.blog.utils.JsonHelper;
import com.blog.vo.MenuTree;

/**
 * @author：Tim
 * @date：2017年9月17日 下午7:42:58
 * @description：角色权限
 */
@Controller
@RequestMapping("/RoleAuth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@RequestMapping("/loadAllMenus")
	@ResponseBody
	public void loadAllMenus(HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");// 解决中文乱码问题。而不需要像以前后台编码、在前端解码

		// 生成树形菜单
		List<MenuTree> allMenusTreeList = authService.getAllMenus();

		StringBuilder strBlderTree = new StringBuilder();
		strBlderTree.append("[{\"id\":0,\"text\":\"菜单\",\"children\":[");

		for (MenuTree menuTree : allMenusTreeList) {
			strBlderTree.append("{");

			strBlderTree.append("\"id\":");
			strBlderTree.append(menuTree.getRoot().getId());
			strBlderTree.append(",");

			strBlderTree.append("\"text\":\"");
			strBlderTree.append(menuTree.getRoot().getMenuName());
			strBlderTree.append("\",");

			strBlderTree.append("\"children\":[");
			for (MenuTree menuTreeChild : menuTree.getChildren()) {
				strBlderTree.append("{");

				strBlderTree.append("\"id\":");
				strBlderTree.append(menuTreeChild.getRoot().getId());
				strBlderTree.append(",");

				strBlderTree.append("\"text\":\"");
				strBlderTree.append(menuTreeChild.getRoot().getMenuName());

				strBlderTree.append("\"},");
			}
			strBlderTree.deleteCharAt(strBlderTree.length() - 1);

			strBlderTree.append("]");
			strBlderTree.append("},");
		}

		strBlderTree.deleteCharAt(strBlderTree.length() - 1);
		strBlderTree.append("]}]");

		PrintWriter out = response.getWriter();
		out.println(strBlderTree.toString());
		out.close();
	}

	@RequestMapping("/getMenuByRoleId")
	@ResponseBody
	public Map<String, Object> getMenuByRoleId(String roleId) {
		List<SysMenu> roleMenu = authService.getMenuByRoleId(roleId);

		return JsonHelper.getModelMap(roleMenu);
	}

	@RequestMapping("/addRoleAuths")
	@ResponseBody
	public Map<String, String> addRoleAuths(HttpServletResponse response, HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String authIds = request.getParameter("authIds");

		//todo事务
		
		// 先删除该角色下的权限
		authService.deleteAuthByRoleId(roleId);

		// 再重新加入权限
		boolean result = authService.addRoleAuths(roleId, authIds);

		return JsonHelper.getSucessResult(result);
	}
}
