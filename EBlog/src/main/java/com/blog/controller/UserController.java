package com.blog.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.lucene.index.MergePolicyWrapper;
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

import com.blog.model.BllArticle;
import com.blog.model.BllArticletype;
import com.blog.model.BllCommont;
import com.blog.model.SysUsers;
import com.blog.service.UserService;
import com.blog.utils.CoreConsts;
import com.blog.utils.HibernateUtils;
import com.blog.utils.ImageCompareHelper;
import com.blog.utils.JsonHelper;
import com.blog.vo.UserSearchParams;

@Controller
@RequestMapping("/User")
public class UserController {

	@Autowired
	private UserService userService;

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

		List<SysUsers> list = userService.searchUser(userSearchParams);

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
		List<SysUsers> list = userService.getUserList();

		return JsonHelper.getModelMapforGrid(list);
	}

	@RequestMapping("/getUserCode")
	@ResponseBody
	public void getUserCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
		List<SysUsers> list = userService.getUserCodeNotCurrent(currentUser.getUserCode());

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
	 * 注册用户。上传图片和保存用户应该作为一个事务
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
		SysUsers user = new SysUsers();
		user.setUserCode(request.getParameter("userCode"));
		user.setUserName(request.getParameter("userName"));
		user.setEmail(request.getParameter("email"));
		user.setUserPassword(request.getParameter("userPassword"));

		if (uploadFile != null) {
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
		SysUsers user = (SysUsers) HibernateUtils.findById(SysUsers.class, userId);
		model.addAttribute("userDTO", user);

		return "admin/system/usersEdit";
	}

	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String, String> updateUser(
			@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile, HttpServletRequest request,
			HttpSession session) throws IllegalStateException, IOException {
		// 获取用户对象
		SysUsers user = new SysUsers();
		user.setId(request.getParameter("id"));
		user.setUserCode(request.getParameter("userCode"));
		user.setUserName(request.getParameter("userName"));
		user.setEmail(request.getParameter("email"));
		user.setUserPassword(request.getParameter("userPassword"));

		if (uploadFile != null) {
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

	@RequestMapping("/deleteUser")
	@ResponseBody
	public Map<String, String> deleteUser(HttpServletResponse response, HttpServletRequest request) {
		String userId = request.getParameter("userId");
		boolean result = userService.deleteUser(userId);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/exportUser")
	public void exportUser(HttpServletResponse response, HttpServletRequest request) {
		String userId = request.getParameter("userId");
		List<SysUsers> userList = userService.getUserListByUserId(userId);
		
		exportUser(userList, response);
	}

	@RequestMapping("/exportAllUser")
	public void exportAllUser(HttpServletResponse response, HttpServletRequest request) {

		List<SysUsers> userList = userService.getUserList();
		exportUser(userList, response);
	}

	private void exportUser(List<SysUsers> userList, HttpServletResponse response) {
		// 创建表格
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("用户");
		HSSFCellStyle cellTxtStyle = wb.createCellStyle();// 普通样式
		HSSFCellStyle titleTxtStyle = wb.createCellStyle();// 标题样式

		cellTxtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		titleTxtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight((short) 1);
		titleTxtStyle.setFont(font);// 红色字

		// 标题
		HSSFRow rowTitle = sheet.createRow(0);
		String[] titles = { "用户名", "登录名", "邮箱" };
		HSSFCell cellTitle = null;
		for (int i = 0; i < titles.length; i++) {
			cellTitle = rowTitle.createCell(i);
			cellTitle.setCellStyle(titleTxtStyle);
			cellTitle.setCellValue(titles[i]);
		}

		HSSFRow rowData = null;
		String[] data = new String[3];// 记录数据
		for (int i = 0; i < userList.size(); i++) {
			// 获取数据
			data[0] = userList.get(i).getUserName();
			data[1] = userList.get(i).getUserCode();
			data[2] = userList.get(i).getEmail();

			rowData = sheet.createRow(i + 1);// 首行为标题

			// 在当前行上创建单元格，并赋值和设置样式
			HSSFCell cellData = null;
			for (int j = 0; j < data.length; j++) {
				cellData = rowData.createCell(j);
				cellData.setCellStyle(cellTxtStyle);
				cellData.setCellValue(data[j]);
			}
		}

//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String fileName = CoreConsts.Runtime.APP_ABSOLUTE_PATH + "download" + File.separator
//				+ dateFormat.format(new Date()) + "用户列表.xls";

		try {
			// // 文件先保存到服务器上，对于大文件夹，后续考虑后台线程下载
			// FileOutputStream outputStream = new FileOutputStream(fileName);
			// wb.write(outputStream);
			// outputStream.close();
			//
			// download(fileName, response);
//			response.addHeader("Conten-Dispotion", "attachment:filename=" + new String(fileName.getBytes()));
//			response.setContentType("application/vnd.ms-excel;charset=gb2312");
//
//			OutputStream outputStream = response.getOutputStream();
//			wb.write(outputStream);
//			outputStream.close();
			
			// 4.获取响应输出流，并将Excel文件写入响应输出流中
			OutputStream out = response.getOutputStream(); // 获取响应输出流
			response.reset(); // 重置请求响应

			// 设置文件名
			String fileNameString = "用户.xls";
			String fileName = new String(fileNameString.getBytes("GBK"), "iso-8859-1");

			response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 设置请求响应头
			response.setContentType("application/msexcel; charset=UTF-8"); // 设置内容类型及编码格式

			wb.write(out); // 将文件写入输出流
			out.flush(); // 执行清空缓存区
			response.flushBuffer();// 执行清空缓存区
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * @param filePath
	 * @param response
	 */
	private void download(String filePath, HttpServletResponse response) {
		try {
			File file = new File(filePath);
			String fileName = file.getName();
			InputStream fInputStream = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buffer = new byte[fInputStream.available()];
			fInputStream.read(buffer);
			fInputStream.close();

			response.reset();
			response.addHeader("Conten-Dispotion", "attachment:filename=" + new String(fileName.getBytes()));
			response.addHeader("Conten-Length", String.valueOf(file.length()));

			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
