package com.blog.model;
// Generated 2017-4-2 12:37:59 by Hibernate Tools 5.2.1.Final

/**
 * BllArticletype generated by hbm2java
 */
public class BllArticletype implements java.io.Serializable {

	private String id;
	private String typeName;
	private String description;
	private String userId;

	public BllArticletype() {
	}

	public BllArticletype(String id) {
		this.id = id;
	}

	public BllArticletype(String id, String typeName, String description, String userId) {
		this.id = id;
		this.typeName = typeName;
		this.description = description;
		this.userId = userId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
