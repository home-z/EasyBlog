package com.blog.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.SysRole;
import com.blog.service.RoleService;
import com.blog.utils.CoreConsts;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;
import com.blog.vo.RoleSearchParams;

/**
 * @author：Tim
 * @date：2017年9月16日 上午9:45:27
 * @description：角色控制器
 */

@Controller
@RequestMapping("/Role")
public class RoleController {
	private static Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	/**
	 * 搜索角色
	 * @return
	 */
	@RequestMapping("/searchRole")
	@ResponseBody
	public Map<String, Object> searchRole(HttpServletRequest request, HttpServletResponse response) {
		RoleSearchParams roleSearchParams = new RoleSearchParams();
		roleSearchParams.setRoleName(request.getParameter("vroleName"));

		List<SysRole> rolesList = roleService.searchRole(roleSearchParams);

		return JsonHelper.getModelMapforGrid(rolesList);
	}

	@RequestMapping("/deleteRole")
	@ResponseBody
	public Map<String, String> deleteRole(HttpServletResponse response, HttpServletRequest request) {
		String roleId = request.getParameter("roleId");
		boolean result = roleService.deleteRole(roleId);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/addRole")
	@ResponseBody
	public Map<String, String> addRole(HttpServletRequest request, HttpSession session) {

		// 新建角色对象
		SysRole role = new SysRole();
		role.setRoleName(request.getParameter("roleName"));
		role.setRemark(request.getParameter("remark"));

		role.setCreateTime(new Date());// 设置创建时间为当前
		role.setCreator(CoreConsts.Runtime.CURRENT_USERCODE);// 设置创建人为当前登录用户

		boolean isRoleNameExist = roleService.isRoleNameExist(role.getRoleName());
		if (isRoleNameExist) {
			return JsonHelper.getSucessResult(false, "该角色名称已经存在！");
		}

		// 保存
		roleService.addRole(role);

		return JsonHelper.getSucessResult(true, "新增角色成功！");
	}

	@RequestMapping("/updateRole")
	@ResponseBody
	public Map<String, String> updateRole(HttpServletRequest request, HttpSession session) {

		SysRole role = (SysRole) HibernateUtils.findById(SysRole.class, request.getParameter("id"));// 获取角色对象
		role.setRoleName(request.getParameter("roleName"));
		role.setRemark(request.getParameter("remark"));
		role.setModifiedTime(new Date());// 设置修改时间为当前
		role.setModifiedtor(CoreConsts.Runtime.CURRENT_USERCODE);// 设置修改人为当前登录用户

		// 修改
		roleService.updateRole(role);

		return JsonHelper.getSucessResult(true, "修改角色成功！");
	}
	
	@RequestMapping("/editRole")
	public String getDetailByRoleId(Model model, @RequestParam(value = "roleId", required = true) String roleId) {
		// 读取角色详细内容
		SysRole role = (SysRole) HibernateUtils.findById(SysRole.class, roleId);
		model.addAttribute("roleDTO", role);

		return "admin/system/rolesEdit";
	}
}
