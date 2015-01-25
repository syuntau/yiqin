package com.yiqin.shop.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yiqin.util.Util;

public class Category implements Serializable {

	private static final long serialVersionUID = 6531351367406306429L;
	// 分类ID
	private String id;
	// 名称
	private String name;
	// 排序号
	private int level;
	// 子分类
	private List<CategorySub> subCategory;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public List<CategorySub> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<CategorySub> subCategory) {
		this.subCategory = subCategory;
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

	public String toString() {
		return Util.objToString(this);
	}
}
