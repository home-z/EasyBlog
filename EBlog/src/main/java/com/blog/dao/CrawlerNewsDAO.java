package com.blog.dao;

import java.util.List;

import com.blog.po.BllPageinfo;

/**
 * @author：Tim
 * @date：2018年1月16日 下午10:36:53
 * @description：TODO
 */
public interface CrawlerNewsDAO {
	List<BllPageinfo> getListCrawlerNewsByUser(String userId);
}
