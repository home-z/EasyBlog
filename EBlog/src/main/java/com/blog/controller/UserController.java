package com.blog.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.blog.constant.RuntimeEnvs;
import com.blog.po.SysUser;
import com.blog.service.BlogService;
import com.blog.service.UserService;
import com.blog.utils.CoreConsts;
import com.blog.utils.HibernateUtils;
import com.blog.utils.ImageCompareHelper;
import com.blog.utils.JsonHelper;
import com.blog.vo.UserSearchParams;

@Controller
@RequestMapping("/User")
public class UserController {

	private static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;

	/**
	 * 搜索用户
	 * @return
	 */
	@RequestMapping("/searchUser")
	@ResponseBody
	public Map<String, Object> searchUser(HttpServletRequest request, HttpServletResponse response) {
		UserSearchParams userSearchParams = new UserSearchParams();
		userSearchParams.setUserCode(request.getParameter("vuserCode"));
		userSearchParams.setUserName(request.getParameter("vuserName"));
		userSearchParams.setEmail(request.getParameter("vemail"));

		List<SysUser> list = userService.searchUser(userSearchParams);

		return JsonHelper.getModelMapforGrid(list);
	}

	/**
	 * 选出所有用户
	 * 
	 * @return Map
	 */
	@RequestMapping("/index")
	@ResponseBody
	public Map<String, Object> getUserList() {
		List<SysUser> list = userService.getUserList();

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/getUserCode")
	@ResponseBody
	public void getUserCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<SysUser> list = userService.getUserCodeNotCurrent(RuntimeEnvs.CURRENT_USERCODE);

		// 拼接Json字符串
		PrintWriter out = response.getWriter();
		StringBuffer strOut = new StringBuffer();

		strOut.append("[");
		for (SysUser user : list) {
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
	 * 注册用户、新增用户。上传图片和保存用户应该作为一个事务
	 * @param user
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/registerUser")
	@ResponseBody
	public Map<String, String> registerUser(
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile, HttpServletRequest request,
			HttpSession session) throws IOException {

		// 获取用户对象
		SysUser user = new SysUser();
		user.setUserCode(request.getParameter("userCode"));
		user.setUserName(request.getParameter("userName"));
		user.setEmail(request.getParameter("email"));
		user.setUserPassword(request.getParameter("userPassword"));
		user.setCreateTime(new Date());// 设置创建时间为当前
		user.setCreator(
				RuntimeEnvs.CURRENT_USERCODE == null ? request.getParameter("userCode") : RuntimeEnvs.CURRENT_USERCODE);// 设置创建人为当前登录用户

		if (uploadFile != null && uploadFile.getSize() != 0) {
			// 上传用户图片
			String userPhotoFileName = uploadFile.getOriginalFilename();// 获取文件名
			String photoDir = session.getServletContext().getRealPath("/admin/userphotos");// 获取存放文件的目录
			File file = new File(photoDir, userPhotoFileName);
			uploadFile.transferTo(file);// 上传文件

			// 用户表存放头像位置
			user.setPhotoPath(file.getPath());
			// 计算用户头像指纹码
			String photoFingerPrint = ImageCompareHelper.produceFingerPrint(file.getPath());
			user.setPhotoFingerPrint(photoFingerPrint);
		}

		boolean isUserExist = userService.isUserCodeExist(user.getUserCode());
		if (isUserExist) {
			return JsonHelper.getSucessResult(false, "该用户名已经存在！");
		}

		// 保存
		userService.addUser(user);

		return JsonHelper.getSucessResult(true, "新增用户成功！");
	}

	@RequestMapping("/editUser")
	public String getDetailByUserId(Model model, @RequestParam(value = "userId", required = true) String userId) {
		// 读取用户详细内容
		SysUser user = (SysUser) HibernateUtils.findById(SysUser.class, userId);
		model.addAttribute("userDTO", user);

		return "admin/system/usersEdit";
	}

	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String, String> updateUser(
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile, HttpServletRequest request,
			HttpSession session) throws IllegalStateException, IOException {

		SysUser user = (SysUser) HibernateUtils.findById(SysUser.class, request.getParameter("id"));// 获取用户对象
		user.setUserName(request.getParameter("userName"));
		user.setEmail(request.getParameter("email"));
		user.setModifier(RuntimeEnvs.CURRENT_USERID);// 设置修改人为当前登录用户

		if (uploadFile != null && uploadFile.getSize() != 0) {
			// 上传用户图片
			String userPhotoFileName = uploadFile.getOriginalFilename();// 获取文件名
			String photoDir = session.getServletContext().getRealPath("/admin/userphotos");// 获取存放文件的目录
			File file = new File(photoDir, userPhotoFileName);
			uploadFile.transferTo(file);// 上传文件

			// 用户表存放头像位置
			user.setPhotoPath(file.getPath());
			// 计算用户头像指纹码
			String photoFingerPrint = ImageCompareHelper.produceFingerPrint(file.getPath());
			user.setPhotoFingerPrint(photoFingerPrint);
		}

		// 修改用户
		userService.updateUser(user);

		return JsonHelper.getSucessResult(true, "修改用户成功！");
	}

	/**
	 * 删除用户
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteUser")
	@ResponseBody
	public Map<String, String> deleteUser(HttpServletResponse response, HttpServletRequest request) {
		String userId = request.getParameter("userId");

		// 校验删除的用户中是否存在发表的博客，发表了博客，则不能删除
		String[] userIds = userId.split(",");
		for (int i = 0; i < userIds.length; i++) {
			if (blogService.getCountByUserId(userIds[i]) > 0) {
				SysUser user = userService.getUserById(userIds[i]);
				return JsonHelper.getSucessResult(false, user.getUserCode() + "有发表的博客，不能删除！");
			}
		}

		boolean result = userService.deleteUser(userId);
		return JsonHelper.getSucessResult(result);
	}

	/**
	 * 导出选择的记录到excel中
	 * @param response
	 * @param request
	 */
	@RequestMapping("/exportUser")
	public void exportUser(HttpServletResponse response, HttpServletRequest request) {
		String userId = request.getParameter("userId");
		List<SysUser> userList = userService.getUserListByUserId(userId);

		exportUser(userList, response);
	}

	@RequestMapping("/isPostArticleByUserCode")
	@ResponseBody
	public Map<String, String> isPostArticleByUserCode(HttpServletResponse response, HttpServletRequest request) {
		String userCode = request.getParameter("userCode");
		int count = blogService.getCountByUserCode(userCode);
		if (count > 0) {
			return JsonHelper.getSucessResult(true);
		} else {
			return JsonHelper.getSucessResult(false);
		}
	}

	/**
	 * 导出全部记录
	 * @param response
	 * @param request
	 */
	@RequestMapping("/exportAllUser")
	public void exportAllUser(HttpServletResponse response, HttpServletRequest request) {
		List<SysUser> userList = userService.getUserList();

		exportUser(userList, response);
	}

	/**
	 * 导出用户列表
	 * @param userList 用户记录
	 * @param response
	 */
	private void exportUser(List<SysUser> userList, HttpServletResponse response) {
		// 创建表格
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("用户");
		HSSFCellStyle cellTxtStyle = wb.createCellStyle();// 普通样式
		HSSFCellStyle titleTxtStyle = wb.createCellStyle();// 标题样式

		sheet.setDefaultColumnWidth(20);// 设置单元格默认宽度
		sheet.createFreezePane(0, 1);// 冻结首行
		cellTxtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		titleTxtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		titleTxtStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 创建一个居中格式

		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.BLUE.index);
		font.setBoldweight((short) 1);
		titleTxtStyle.setFont(font);// 标题蓝色字

		// 标题
		HSSFRow rowTitle = sheet.createRow(0);
		String[] titles = { "用户名", "姓名", "邮箱", "创建时间", "创建人", "修改时间", "修改人" };
		HSSFCell cellTitle = null;
		for (int i = 0; i < titles.length; i++) {
			cellTitle = rowTitle.createCell(i);
			cellTitle.setCellStyle(titleTxtStyle);
			cellTitle.setCellValue(titles[i]);
		}

		HSSFRow rowData = null;
		String[] data = new String[7];// 记录数据
		HSSFCell cellData = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化时间对象
		for (int i = 0; i < userList.size(); i++) {
			// 获取数据
			data[0] = userList.get(i).getUserCode();
			data[1] = userList.get(i).getUserName();
			data[2] = userList.get(i).getEmail();
			data[3] = dateFormat.format(userList.get(i).getCreateTime());
			data[4] = userList.get(i).getCreator();
			data[5] = userList.get(i).getModifyTime() == null ? "" : dateFormat.format(userList.get(i).getModifyTime());
			data[6] = userList.get(i).getModifier();

			rowData = sheet.createRow(i + 1);// 首行为标题

			// 在当前行上创建单元格，并赋值和设置样式
			for (int j = 0; j < data.length; j++) {
				cellData = rowData.createCell(j);
				cellData.setCellStyle(cellTxtStyle);
				cellData.setCellValue(data[j]);
			}
		}

		// 设置文件名
		SimpleDateFormat dateFileFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fileNameString = dateFileFormat.format(new Date()) + "用户列表.xls";
		try {
			String fileName = new String(fileNameString.getBytes("UTF-8"), "iso-8859-1");

			// 获取响应输出流，并将Excel文件写入响应输出流中
			OutputStream out = response.getOutputStream(); // 获取响应输出流
			response.reset(); // 重置请求响应

			response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 设置请求响应头
			response.setContentType("application/msexcel; charset=UTF-8"); // 设置内容类型及编码格式

			wb.write(out); // 将文件写入输出流
			out.flush(); // 执行清空缓存区
			response.flushBuffer();// 执行清空缓存区
			out.close();
		} catch (Exception e) {
			logger.error(e.getStackTrace());
		}
	}

}
