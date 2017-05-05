package com.blog.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
			throws UnsupportedEncodingException {
		String keyWord = request.getParameter("keyword");

		Map<String, String> shouldMap = new HashMap<String, String>();
		shouldMap.put("title", keyWord);
		shouldMap.put("content", keyWord);

		return ElasticSearchUtils.multiOrSearchDocHigh("bll_article", shouldMap);
	}

}
