package com.blog.controller;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.service.BlogService;
import com.blog.utils.ElasticSearchUtils;

/**
 * @author：Tim
 * @date：2017年5月5日 下午9:38:49
 * @description：操作blog类与ElasticSearch
 */

@Controller
@RequestMapping("/BlogSearch")
public class BlogESController {

	@RequestMapping("/searchBlog")
	@ResponseBody
	public Map<String, Object> searchBlog(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException, SQLException {
		String keyWord = request.getParameter("keyword");// 读取前端输入的关键词

		// 该关键词可能是标题或者内容，使用or进行查询
		Map<String, String> shouldMap = new HashMap<String, String>();
		shouldMap.put("title", keyWord);
		shouldMap.put("content", keyWord);

		// es查询结果
		Map<String, Object> mapResutl = ElasticSearchUtils.multiOrSearchDocHigh("bll_article", shouldMap, 0, 10);

		// 在搜索返回的结果中加入其他信息，这些信息是从mysql数据库中读取的，es只返回基本信息，这里加上搜索结果的其他信息，用于前端展示
		List<Map<String, Object>> list = (List<Map<String, Object>>) mapResutl.get("rows");
		for (Map<String, Object> mapRow : list) {
			String articleId = (String) mapRow.get("id");
			ResultSet rs = BlogService.getPostInfo(articleId);
			while (rs.next()) {
				mapRow.put("createBy", rs.getString("CreateBy"));
				mapRow.put("createTime", rs.getString("CreateTime"));
				mapRow.put("readCount", rs.getString("ReadCount"));
				mapRow.put("suggestCount", rs.getString("SuggestCount"));
				mapRow.put("comCount", rs.getString("ComCount"));
			}
		}

		return mapResutl;
	}

}
