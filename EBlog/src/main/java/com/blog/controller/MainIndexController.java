package com.blog.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.blog.service.MainIndexService;
import com.blog.utils.CoreConsts;
import com.blog.utils.DateBindController;
import com.blog.utils.HibernateUtils;
import com.blog.utils.JsonHelper;

@Controller
@RequestMapping("/MainIndex")
public class MainIndexController extends DateBindController {

	private static Logger logger = Logger.getLogger(MainIndexController.class);

	//spring注解，表示需要自动装配，根据在spingmvc-confog.xml中配置的包，根据类型找对应的bean，bean需要用@注解
	@Autowired
	private MainIndexService mainIndexService;

	int pCount = CoreConsts.ExecuteContextKeys.PAGESIZE;// 每页显示记录数目

	// 生成分页按钮
	@RequestMapping("/getArticlePage")
	@ResponseBody
	public Map<String, Object> getArticlePage(HttpServletRequest request,
			@RequestParam(value = "action", required = true) String action) {
		
	    RequestContext requestContext = new RequestContext(request);//读取多语资源

		String url = request.getContextPath() + action;
		boolean isType = url.contains("typeid=") ? true : false;// 判断是否是点击了分类，分类则url带有类别参数

		int toalCount = mainIndexService.getArticlePage(url);// 总数
		int page = (toalCount % pCount == 0) ? (toalCount / pCount) : (toalCount / pCount + 1);// 总页数

		// 拼接分页html
		StringBuffer strBPageHtml = new StringBuffer();
		strBPageHtml.append("<p>");
		strBPageHtml.append(requestContext.getMessage("all"));
		strBPageHtml.append(page);
		if (toalCount>0) {
			strBPageHtml.append(requestContext.getMessage("pages"));
		}else {
			strBPageHtml.append(requestContext.getMessage("page"));
		}

		strBPageHtml.append("&nbsp;");
		for (int i = 1; i < page + 1; i++) {
			if (i == 1) {
				strBPageHtml.append("<a class='aPageDisable' href='javascript:void(0);'");
			} else {
				strBPageHtml.append("<a href='javascript:void(0);'");
			}

			strBPageHtml.append(" onclick=\"getArticle('");
			strBPageHtml.append(url);
			if (isType) {
				strBPageHtml.append("&page=");
			} else {
				strBPageHtml.append("?page=");
			}

			strBPageHtml.append(i);
			strBPageHtml.append("')\">");
			strBPageHtml.append(i);
			strBPageHtml.append("</a>&nbsp;");
		}
		strBPageHtml.append("</p>");

		return JsonHelper.getModel(strBPageHtml.toString());
	}

	// 获取分类
	@RequestMapping("/getCategory")
	@ResponseBody
	public Map<String, Object> getCategory(HttpServletRequest request) throws SQLException {
		StringBuffer strBCategory = new StringBuffer();
		strBCategory.append("<ul>");

		ResultSet rs = mainIndexService.getCategory();
		while (rs.next()) {
			strBCategory.append("<li><a onClick='addTypeMenu(\"");
			strBCategory.append(rs.getString("typename"));
			strBCategory.append("\",\"");
			strBCategory.append(rs.getString("typeid"));
			strBCategory.append("\")");
			strBCategory.append("' href=\"#\" >");
			strBCategory.append(rs.getString("typename"));
			strBCategory.append("(");
			strBCategory.append(rs.getString("countn"));
			strBCategory.append(")");
			strBCategory.append("</a></li>");
		}

		strBCategory.append("</ul>");
		
		logger.debug("获取分类");

		return JsonHelper.getModel(strBCategory.toString());
	}

	// 按照文章分类读取该分类下的文章
	@RequestMapping("/getArticleByType")
	@ResponseBody
	public Map<String, Object> getArticleByType(Model model,
			@RequestParam(value = "typeid", required = true) String typeid,
			@RequestParam(value = "page", required = true) String page) {

		List<BllArticle> newList = removeArtilceHtml(mainIndexService.getArticleByType(typeid, page));
		model.addAttribute("dto", newList);

		return JsonHelper.getModelMap(newList);
	}

	// 分页读取文章，按照时间先后顺序排列
	@RequestMapping("/getallArticle")
	@ResponseBody
	public Map<String, Object> getallArticleList(@RequestParam(value = "page", required = true) String page) {
		List<BllArticle> newList = removeArtilceHtml(mainIndexService.getArticleBy(0, page));

		return JsonHelper.getModelMap(newList);
	}

	// 按照阅读量排列
	@RequestMapping("/getArticleRead")
	@ResponseBody
	public Map<String, Object> getArticleRead(@RequestParam(value = "page", required = true) String page) {
		List<BllArticle> newList = removeArtilceHtml(mainIndexService.getArticleBy(1, page));

		return JsonHelper.getModelMap(newList);
	}

	// 按照评论量排列
	@RequestMapping("/getArticleCommit")
	@ResponseBody
	public Map<String, Object> getArticleCommit(@RequestParam(value = "page", required = true) String page) {
		List<BllArticle> newList = removeArtilceHtml(mainIndexService.getArticleBy(2, page));

		return JsonHelper.getModelMap(newList);
	}

	// 按照推荐量排列
	@RequestMapping("/getArticleSuggest")
	@ResponseBody
	public Map<String, Object> getArticleSuggest(@RequestParam(value = "page", required = true) String page) {
		List<BllArticle> newList = removeArtilceHtml(mainIndexService.getArticleBy(3, page));

		return JsonHelper.getModelMap(newList);
	}

	// 读取某个文章的评论数
	@RequestMapping("/getSingleComm")
	@ResponseBody
	public Map<String, Object> getSingleComm(String id) {
		int count = mainIndexService.getSingleComm(id);

		return JsonHelper.getModel(count);
	}

	// 通过文章id，读取文章信息和评论
	@RequestMapping("/getDetailById")
	public String getDetailById(Model model, @RequestParam(value = "id", required = true) String id) {
		// 读取文件详细内容
		BllArticle article = (BllArticle) HibernateUtils.findById(BllArticle.class, id);
		model.addAttribute("artdto", article);

		// 读取该文章的评论
		List<BllCommont> comList = mainIndexService.getDetailById(id);
		model.addAttribute("comList", comList);

		return "blog/article/articleView";// 跳转到该用户文章浏览页
	}

	// 读取该用户的所有博客
	@RequestMapping("/getArticleByCreateBy")
	public String getArticleByCreateBy(Model model, @RequestParam(value = "user", required = true) String user) {
		List<BllArticle> newList = removeArtilceHtml(mainIndexService.getArticleByCreateBy(user));
		model.addAttribute("dto", newList);

		return "blog/article/articleViewlistuser";// 跳转到该用户页面，显示该用户所有文章
	}

	/**
	 * 用于首页列表展示时，只需要列出部分内容，且不需要展示样式。故过滤掉样式及空格
	 * 
	 * @param articles源文章list
	 * @return 过滤后的文章list
	 */
	private List<BllArticle> removeArtilceHtml(List<BllArticle> articles) {
		List<BllArticle> newarticle = null;
		if (articles != null && articles.size() > 0) {
			newarticle = new ArrayList<BllArticle>();
			for (int i = 0; i < articles.size(); i++) {
				String artc = articles.get(i).getContent().replaceAll("<.*?>", "").replace("&nbsp;", "");
				if (artc != null && artc.length() > 200) {
					artc = artc.substring(0, 200) + " ... ";
				}
				articles.get(i).setContent(artc);
				newarticle.add(i, articles.get(i));
			}
		} else {
			return articles;
		}

		return newarticle;
	}
}
