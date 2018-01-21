package com.blog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.po.SysUser;
import com.blog.service.RoleUserService;
import com.blog.utils.JsonHelper;

/**
 * @author：Tim
 * @date：2017年9月16日 下午5:56:41
 * @description：角色下用户
 */

@Controller
@RequestMapping("/RoleUser")
public class RoleUserController {

	@Autowired
	private RoleUserService roleUserService;

	@RequestMapping("/getRoleUser")
	@ResponseBody
	public Map<String, Object> getRoleUser(HttpServletRequest request, HttpServletResponse response) {
		String roleId = request.getParameter("roleId");

		List<SysUser> usersList = roleUserService.getRoleUser(roleId);

		return JsonHelper.getModelMapforGrid(usersList);
	}

	@RequestMapping("/addRoleUser")
	@ResponseBody
	public Map<String, String> addRoleUser(HttpServletResponse response, HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String userIds = request.getParameter("userIds");

		// 判断角色中是否已经存在选择的用户
		String[] userIdsArray = userIds.split(",");
		for (int i = 0; i < userIdsArray.length; i++) {
			if (roleUserService.isExistRoleUser(roleId, userIdsArray[i])) {
				return JsonHelper.getSucessResult(false, userIdsArray[i] + "已经在该角色中，请重新选择！");
			}
		}

		boolean result = roleUserService.addRoleUser(roleId, userIds);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/removeRoleUser")
	@ResponseBody
	public Map<String, String> removeRoleUser(HttpServletResponse response, HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		String userCodes = request.getParameter("userCodes");

		boolean result = roleUserService.removeRoleUser(roleId, userCodes);

		return JsonHelper.getSucessResult(result);
	}
}
