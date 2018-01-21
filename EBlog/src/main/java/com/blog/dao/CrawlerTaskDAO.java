package com.blog.dao;

import java.util.List;

import com.blog.po.BllCrawltask;
import com.blog.po.BllFavarticle;

/**
 * @author：Tim
 * @date：2018年1月15日 下午10:04:40
 * @description：TODO
 */
public interface CrawlerTaskDAO {
	List<BllCrawltask> getListCrawlerTaskByUser(String userId);

	boolean removeCrawTask(String toDeleteIds);

	boolean addCrawlerTask(BllCrawltask crawltask);
}
