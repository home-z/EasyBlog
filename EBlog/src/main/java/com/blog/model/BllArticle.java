package com.blog.model;
// Generated 2017-4-2 12:37:59 by Hibernate Tools 5.2.1.Final

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.blog.utils.CustomDateSerializer;

/**
 * 2017-05-05修改 
 * 1、日期加注解，转换成标准格式 
 * 2、设置默认值
 * TODO 对于跳转后的页面，时间格式不起作用，例如viewlistuser.jsp界面
 */
public class BllArticle implements java.io.Serializable {

	private String id;
	private String typeId;
	private String title;
	private String content;
	private Date createTime;
	private String createBy;
	private Date modifyTime;
	private Integer comCount;
	private Integer readCount;
	private String typeName;
	private Integer suggestCount;

	public BllArticle() {
	}

	public BllArticle(String id) {
		this.id = id;
	}

	public BllArticle(String id, String typeId, String title, String content, Date createTime, String createBy,
			Date modifyTime, Integer comCount, Integer readCount, String typeName, Integer suggestCount) {
		this.id = id;
		this.typeId = typeId;
		this.title = title;
		this.content = content;
		this.createTime = createTime;
		this.createBy = createBy;
		this.modifyTime = modifyTime;
		this.comCount = comCount;
		this.readCount = readCount;
		this.typeName = typeName;
		this.suggestCount = suggestCount;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return this.createTime;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getModifyTime() {
		return this.modifyTime;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getComCount() {
		return this.comCount;
	}

	public void setComCount(Integer comCount) {
		this.comCount = comCount == null ? 0 : comCount;
	}

	public Integer getReadCount() {
		return this.readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount == null ? 0 : readCount;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSuggestCount() {
		return this.suggestCount;
	}

	public void setSuggestCount(Integer suggestCount) {
		this.suggestCount = suggestCount == null ? 0 : suggestCount;
	}

}
