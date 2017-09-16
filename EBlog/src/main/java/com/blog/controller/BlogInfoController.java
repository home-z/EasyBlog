package com.blog.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.blog.model.BllArticle;
import com.blog.model.BllCommont;
import com.blog.model.SysUsers;
import com.blog.service.BlogService;
import com.blog.utils.CoreConsts;
import com.blog.utils.ElasticSearchUtils;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;

@Controller
@RequestMapping("/BlogInfo")
public class BlogInfoController {

	private static Logger logger = Logger.getLogger(BlogInfoController.class);

	// 调用DAL层
	@Autowired
	private BlogService blogService;

	@RequestMapping("/searchBlog")
	@ResponseBody // 将返回值ResultInfo实体转化为json
	public void searchBlog(HttpServletRequest request, HttpServletResponse response) throws InstantiationException,
			IllegalAccessException, UnsupportedEncodingException, IOException, ParseException {
		// 获取当前登录的用户
		SysUsers currentUser = (SysUsers) request.getSession().getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);

		String vblogType = request.getParameter("vblogType") == null ? "" : request.getParameter("vblogType");
		String vTitle = request.getParameter("vTitle") == null ? "" : request.getParameter("vTitle");
		String vstartDate = request.getParameter("vstartDate") == null ? "" : request.getParameter("vstartDate");
		String vendDate = request.getParameter("vendDate") == null ? "" : request.getParameter("vendDate");
		String vContent = request.getParameter("vContent") == null ? "" : request.getParameter("vContent");

		List<BllArticle> blogs = new ArrayList<BllArticle>();
		blogs = blogService.searchBlog(vblogType, URLDecoder.decode(vTitle, "UTF-8"), vstartDate, vendDate,
				URLDecoder.decode(vContent, "UTF-8"), currentUser.getUserCode());

		// 拼接Json字符串
		PrintWriter out = response.getWriter();
		StringBuffer strOut = new StringBuffer();
		strOut.append("[");
		String strJsonString = "";
		if (blogs.size() > 0) {
			for (BllArticle blog : blogs) {
				strOut.append("{");
				strOut.append("\"BlogID\":\"" + blog.getId() + "\",");
				strOut.append("\"Title\":\"" + URLEncoder.encode(blog.getTitle().toString(), "UTF-8") + "\",");
				strOut.append("\"CreateTime\":\"" + blog.getCreateTime().toString() + "\",");
				strOut.append("\"BlogTypeID\":\"" + blog.getTypeId().toString() + "\",");
				strOut.append("\"BlogTypeName\":\"" + blog.getTypeName().toString() + "\"");
				strOut.append("},");
			}
			strJsonString = strOut.substring(0, strOut.length() - 1);
		} else {
			strJsonString = strOut.toString();
		}

		strJsonString += "]";

		out.println(strJsonString);
		out.close();
	}

	// 通过文章id，读取文章信息和评论
	@RequestMapping("/edit")
	public String getDetailById(Model model, @RequestParam(value = "blogid", required = true) String blogid) {
		// 读取文章详细内容
		BllArticle article = (BllArticle) HibernateUtils.findById(BllArticle.class, blogid);
		model.addAttribute("artdto", article);

		// 读取该文章的评论
		List<BllCommont> comList = blogService.getDetailById(blogid);

		return "admin/blog/bloginfo";
	}

	@RequestMapping("/saveInfo")
	@ResponseBody
	public void saveInfo(BllArticle article, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		if (article.getId() == null || article.getId() == "") {
			// 新增
			// 获取当前登录的用户
			SysUsers currentUser = (SysUsers) request.getSession()
					.getAttribute(CoreConsts.ExecuteContextKeys.CURRENT_USER);
			article.setId(UUID.randomUUID().toString());
			article.setCreateBy(currentUser.getUserCode());
			article.setCreateTime(new Date());// new Date()为获取当前系统时间
			article.setModifyTime(new Date());
			article.setComCount(0);
			article.setReadCount(0);
			article.setSuggestCount(0);

			article.setTypeName(request.getParameter("currenttypeName"));// 获取类别名称

			if (HibernateUtils.add(article)) {
				// 新增文章加入到elasticSearch中
				ElasticSearchUtils.addDoc("bll_article", article.getId(), article, "getId", "getTitle", "getContent");

				PrintWriter out = response.getWriter();
				response.setContentType("text/html; charset=utf-8");

				out.print("<script>alert('新增文章成功！');self.location=document.referrer;</script>");// 弹窗，返回上一页并刷新
				out.close();
			}
		} else {
			// form提交，只获取页面上有的值
			String oldCreateTime = request.getParameter("oldcreateTime");
			article.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldCreateTime));
			article.setComCount(Integer.parseInt(request.getParameter("oldcomCount")));
			article.setReadCount(Integer.parseInt(request.getParameter("oldreadCount")));
			article.setModifyTime(new Date());
			article.setTypeName(request.getParameter("currenttypeName"));// 获取类别名称

			if (HibernateUtils.update(article)) {
				// 更新内容更新到elasticSearch中
				Map<String, String> updateParam = new HashMap<String, String>();
				updateParam.put("title", article.getTitle());
				updateParam.put("content", article.getContent());

				ElasticSearchUtils.updateDoc("bll_article", article.getId(), updateParam);

				PrintWriter out = response.getWriter();
				response.setContentType("text/html; charset=utf-8");

				out.print("<script>alert('更新文章成功！');self.location=document.referrer;</script>");// 弹窗，返回上一页并刷新
				out.close();
			}
		}
	}

	@RequestMapping("/deleteBlog")
	@ResponseBody
	public Map<String, String> deleteBlog(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		String toDeleteId = request.getParameter("vblogid");

		if (HibernateUtils.delete(HibernateUtils.findById(BllArticle.class, toDeleteId))) {
			// 同时删除elasticSearch中记录
			ElasticSearchUtils.deleteDoc("bll_article", toDeleteId);

			return JsonHelper.getSucessResult(true);
		} else {
			return JsonHelper.getSucessResult(false);
		}
	}

	@RequestMapping("/getBlogStatistics")
	@ResponseBody // 将返回值ResultInfo实体转化为json
	public Map<String, Object> getBlogStatistics(HttpServletRequest request, HttpServletResponse response)
			throws SQLException {

	    RequestContext requestContext = new RequestContext(request);//读取多语资源
		
		String styleType = request.getParameter("styleType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		StringBuffer strBlogPost = new StringBuffer();
		strBlogPost.append("[");
		String strPost = "";

		// 调用DAL层
		ResultSet rs = blogService.getBlogStatistics(styleType, startDate, endDate);
		while (rs.next()) {
			strBlogPost.append("{");
			strBlogPost.append("\"group\":");
			strBlogPost.append("\"");
			strBlogPost.append(requestContext.getMessage("blog"));
			strBlogPost.append("\",");
			strBlogPost.append("\"name\":");
			strBlogPost.append("\"" + rs.getString("postDate") + "\",");
			strBlogPost.append("\"value\":");
			strBlogPost.append("\"" + rs.getString("postCount") + "\"");
			strBlogPost.append("},");
		}
		if (strBlogPost.length() > 1) {
			strPost = strBlogPost.substring(0, strBlogPost.length() - 1);
		} else {
			strPost = strBlogPost.toString();
		}

		strPost = strPost + "]";

		return JsonHelper.getModel(strPost);
	}
}
