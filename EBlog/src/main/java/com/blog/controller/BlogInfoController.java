package com.blog.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.blog.constant.SystemEnvs;
import com.blog.po.BllArticle;
import com.blog.po.BllCommont;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.utils.ArticleUtils;
import com.blog.utils.JsonHelper;
import com.blog.utils.SessionHelper;
import com.blog.vo.ArticleAddRequest;
import com.blog.vo.ArticleIndexResponse;
import com.blog.vo.ArticleSearchParams;
import com.blog.vo.ArticleStatisticResponse;
import com.blog.vo.ArticleUpdateRequest;

@Controller
@RequestMapping("/BlogInfo")
public class BlogInfoController extends BaseController {

	private static Logger logger = Logger.getLogger(BlogInfoController.class);

	// spring注解，表示需要自动装配，根据在spingmvc-confog.xml中配置的包，根据类型找对应的bean，bean需要用@注解
	@Autowired
	private BlogService blogService;

	@Autowired
	private CommentService commentService;

	int pCount = SystemEnvs.PAGESIZE;// 每页显示记录数目

	@RequestMapping("/searchBlog")
	@ResponseBody // 将返回值ResultInfo实体转化为json
	public Map<String, Object> searchArticle(ArticleSearchParams articleSearchParams) {
		articleSearchParams.setUserId(SessionHelper.getCurrentUserId(request));
		List<BllArticle> blogs = blogService.searchArticle(articleSearchParams);

		return JsonHelper.getModelMapforGrid(blogs);
	}

	// 通过文章id，读取文章信息
	@RequestMapping("/getDetailById")
	public String getDetailById(Model model, @RequestParam(value = "blogid", required = true) String blogid) {
		// 读取文章详细内容
		BllArticle article = blogService.getArticleById(blogid);
		model.addAttribute("articleDTO", article);

		return "admin/blog/bloginfo";
	}

	@RequestMapping("/saveBlog")
	@ResponseBody
	public Map<String, String> saveBlog(ArticleAddRequest articleAdd) {
		BllArticle article = new BllArticle();

		article.setId(UUID.randomUUID().toString());
		article.setTitle(articleAdd.getTitle());
		article.setTypeId(articleAdd.getBlogTypeId());
		article.setTypeName(articleAdd.getBlogTypeName());
		article.setContent(articleAdd.getContent());
		article.setCreator(SessionHelper.getCurrentUserId(request));

		boolean result = blogService.addArticle(article);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/updateBlog")
	@ResponseBody
	public Map<String, String> updateBlog(ArticleUpdateRequest articleUpdate) {
		BllArticle article = blogService.getArticleById(articleUpdate.getBlogid());

		article.setTitle(articleUpdate.getTitle());
		article.setTypeId(articleUpdate.getBlogTypeId());
		article.setTypeName(articleUpdate.getBlogTypeName());
		article.setContent(articleUpdate.getContent());
		article.setModifier(SessionHelper.getCurrentUserId(request));

		boolean result = blogService.updateArticle(article);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/deleteBlog")
	@ResponseBody
	public Map<String, String> deleteBlog(String blogid) {
		boolean result = blogService.deleteArticleById(blogid);

		logger.info("删除文章。" + blogid);

		return JsonHelper.getSucessResult(result);
	}

	@RequestMapping("/getBlogStatistics")
	@ResponseBody // 将返回值ResultInfo实体转化为json
	public Map<String, Object> getBlogStatistics(String styleType, String startDate, String endDate) {
		RequestContext requestContext = new RequestContext(request);// 读取多语资源

		StringBuffer strBlogPost = new StringBuffer();
		strBlogPost.append("[");
		String strPost = "";

		List<ArticleStatisticResponse> lStatisticResponses = blogService.getBlogStatistics(styleType, startDate,
				endDate);

		for (ArticleStatisticResponse articleStatisticResponse : lStatisticResponses) {
			strBlogPost.append("{");
			strBlogPost.append("\"group\":");
			strBlogPost.append("\"");
			strBlogPost.append(requestContext.getMessage("blog"));
			strBlogPost.append("\",");
			strBlogPost.append("\"name\":");
			strBlogPost.append("\"" + articleStatisticResponse.getPostDate() + "\",");
			strBlogPost.append("\"value\":");
			strBlogPost.append("\"" + articleStatisticResponse.getPostCount() + "\"");
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

	// 生成分页按钮
	@RequestMapping("/getArticlePage")
	@ResponseBody
	public Map<String, Object> getArticlePage(@RequestParam(value = "action", required = true) String action) {

		RequestContext requestContext = new RequestContext(request);// 读取多语资源

		String url = request.getContextPath() + action;
		boolean isType = url.contains("typeid=") ? true : false;// 判断是否是点击了分类，分类则url带有类别参数

		int toalCount = blogService.getArticlePage(url);// 总数
		int page = (toalCount % pCount == 0) ? (toalCount / pCount) : (toalCount / pCount + 1);// 总页数

		// 拼接分页html
		StringBuffer strBPageHtml = new StringBuffer();
		strBPageHtml.append("<p>");
		strBPageHtml.append(requestContext.getMessage("all"));
		strBPageHtml.append(page);
		if (toalCount > 0) {
			strBPageHtml.append(requestContext.getMessage("pages"));
		} else {
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

	// 按照文章分类读取该分类下的文章
	@RequestMapping("/getArticleByType")
	@ResponseBody
	public Map<String, Object> getArticleByType(Model model,
			@RequestParam(value = "typeid", required = true) String typeid,
			@RequestParam(value = "page", required = true) String page) {
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByType(typeid, page));
		model.addAttribute("dto", newList);

		return JsonHelper.getModelMap(newList);
	}

	// 分页读取文章，按照时间先后顺序排列
	@RequestMapping("/getallArticle")
	@ResponseBody
	public Map<String, Object> getallArticleList(@RequestParam(value = "page", required = true) String page) {
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByOrderType(0, page));

		return JsonHelper.getModelMap(newList);
	}

	// 按照阅读量排列
	@RequestMapping("/getArticleRead")
	@ResponseBody
	public Map<String, Object> getArticleRead(@RequestParam(value = "page", required = true) String page) {
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByOrderType(1, page));

		return JsonHelper.getModelMap(newList);
	}

	// 按照评论量排列
	@RequestMapping("/getArticleCommit")
	@ResponseBody
	public Map<String, Object> getArticleCommit(@RequestParam(value = "page", required = true) String page) {
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByOrderType(2, page));

		return JsonHelper.getModelMap(newList);
	}

	// 按照推荐量排列
	@RequestMapping("/getArticleSuggest")
	@ResponseBody
	public Map<String, Object> getArticleSuggest(@RequestParam(value = "page", required = true) String page) {
		List<ArticleIndexResponse> newList = ArticleUtils
				.removeArticleIndexResponseHtml(blogService.getArticleByOrderType(3, page));

		return JsonHelper.getModelMap(newList);
	}

	// 通过文章id，读取文章信息和评论
	@RequestMapping("/getDetailByIdView")
	public String getDetailByIdView(Model model, @RequestParam(value = "id", required = true) String id) {
		// 读取文件详细内容
		ArticleIndexResponse articleIndexResponse = blogService.getDetailByIdView(id);
		model.addAttribute("artdto", articleIndexResponse);

		// 读取该文章的评论
		List<BllCommont> comList = commentService.getCommentById(id);
		model.addAttribute("comList", comList);

		return "blog/article/articleView";// 跳转到该用户文章浏览页
	}

	// 读取该用户的所有博客
	@RequestMapping("/getArticleByCreateBy")
	public String getArticleByCreateBy(Model model, @RequestParam(value = "userId", required = true) String userId) {
		List<BllArticle> newList = ArticleUtils.removeArtilceHtml(blogService.getArticleByCreator(userId));
		model.addAttribute("dto", newList);

		return "blog/article/articleViewlistuser";// 跳转到该用户页面，显示该用户所有文章
	}
}
