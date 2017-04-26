package com.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.BllArticletype;
import com.blog.model.SysUsers;
import com.blog.utils.CoreConsts;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;

@Controller
@RequestMapping("/User")
public class UserController {
	
	/**
	 * 选出所有用户
	 * 
	 * @return Map
	 */
	@RequestMapping("/index")
	@ResponseBody
	public Map<String, Object> getUserList() {
		List<SysUsers> list = HibernateUtils.queryListParam(SysUsers.class, "select * from sys_users");

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/getUserCode")
	@ResponseBody
	public void getUserCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<SysUsers> list = HibernateUtils.queryListParam(SysUsers.class,
				"select * from sys_users where usercode!='" + currentUser.getUserCode() + "'");

		// 拼接Json字符串
		PrintWriter out = response.getWriter();
		StringBuffer strOut = new StringBuffer();

		strOut.append("[");

		for (SysUsers user : list) {
			strOut.append("{");
			strOut.append("\"id\":\"" + user.getId() + "\",");
			strOut.append("\"text\":\"" + user.getUserCode() + "\"");
			strOut.append("},");
		}

		String strJsonString = strOut.substring(0, strOut.length() - 1);
		strJsonString += "]";

		out.println(strJsonString);
		out.close();
	}

	/**
	 * 注册用户
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/registerUser")
	@ResponseBody
	public void registerUser(SysUsers user, HttpServletResponse response) throws IOException {
		SysUsers usertest = (SysUsers) HibernateUtils.findById(SysUsers.class, user.getUserCode());
		if (usertest != null) {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=utf-8");

			out.print("<script>alert('用户名为：" + user.getUserCode() + "的用户已经存在，请重新输入用户名！');history.back();</script>");
			out.close();
		} else {
			user.setId(UUID.randomUUID().toString());// 生成一个id

			if (HibernateUtils.add(user))// 保存
			{
				PrintWriter out = response.getWriter();
				response.setContentType("text/html; charset=utf-8");

				out.print("<script>alert('注册用户成功！');self.location=document.referrer;</script>");// 弹窗，返回上一页并刷新
				out.close();
			}
		}
	}

	@RequestMapping("/addUser")
	@ResponseBody
	public void addUser(HttpServletResponse response, SysUsers user) throws IOException {
		SysUsers usertest = (SysUsers) HibernateUtils.findById(SysUsers.class, user.getUserCode());
		if (usertest != null) {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=utf-8");

			out.print("<script>alert('用户名为：" + user.getUserCode() + "的用户已经存在，请重新输入用户名！');history.back();</script>");
			out.close();
		} else {
			user.setId(UUID.randomUUID().toString());
			HibernateUtils.add(user);

			PrintWriter out = response.getWriter();
			response.setContentType("text/html; charset=utf-8");

			out.print("<script>alert('用户名为：" + user.getUserCode() + "的用户新增成功！');history.back();</script>");
			out.close();
		}
	}

	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String, String> updateUser(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String getupdateRowJson = new String(request.getParameter("updateRowJson").getBytes("ISO-8859-1"), "UTF-8");
		List updateUserlst = JsonHelper.getListFromJsonArrStr(getupdateRowJson, SysUsers.class);

		for (int i = 0; i < updateUserlst.size(); i++) {
			SysUsers user = (SysUsers) updateUserlst.get(i);

			HibernateUtils.update(user);
		}

		return JsonHelper.getSucessResult(true);
	}

	@RequestMapping("/deleteUser")
	@ResponseBody
	public Map<String, String> deleteUser(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String getdeleteRowsJson = new String(request.getParameter("deleteRowsJson").getBytes("ISO-8859-1"), "UTF-8");
		List deleteUserlst = JsonHelper.getListFromJsonArrStr(getdeleteRowsJson, SysUsers.class);

		for (int i = 0; i < deleteUserlst.size(); i++) {
			SysUsers user = (SysUsers) deleteUserlst.get(i);

			HibernateUtils.delete(user);
		}

		return JsonHelper.getSucessResult(true);
	}
}
