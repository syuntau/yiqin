package com.yiqin.shop.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yiqin.util.Util;

public class Category implements Serializable {

	private static final long serialVersionUID = 6531351367406306429L;
	// 分类ID
	private int id;
	// 名称
	private String name;
	// 排序号
	private int level;
	// 父分类ID
	private int parentId;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 子分类列表
	private List<Category> subCategoryList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public List<Category> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<Category> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
