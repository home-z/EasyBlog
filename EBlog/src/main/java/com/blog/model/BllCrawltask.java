package com.blog.model;
// Generated 2017-4-2 12:37:59 by Hibernate Tools 5.2.1.Final

import java.util.Date;

/**
 * BllCrawltask generated by hbm2java
 */
public class BllCrawltask implements java.io.Serializable {

	private String id;
	private String createBy;
	private String crawlUrl;
	private String keyWords;
	private Date createTime;
	private Integer state;
	private Date finishTime;

	public BllCrawltask() {
	}

	public BllCrawltask(String id) {
		this.id = id;
	}

	public BllCrawltask(String id, String createBy, String crawlUrl, String keyWords, Date createTime, Integer state,
			Date finishTime) {
		this.id = id;
		this.createBy = createBy;
		this.crawlUrl = crawlUrl;
		this.keyWords = keyWords;
		this.createTime = createTime;
		this.state = state;
		this.finishTime = finishTime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCrawlUrl() {
		return this.crawlUrl;
	}

	public void setCrawlUrl(String crawlUrl) {
		this.crawlUrl = crawlUrl;
	}

	public String getKeyWords() {
		return this.keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

}
