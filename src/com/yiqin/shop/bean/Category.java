package com.yiqin.shop.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yiqin.util.Util;

public class Category implements Serializable {

	private static final long serialVersionUID = 6531351367406306429L;
	private int id;                                             // 分类ID
	private String name;                                  // 名称
	private int level;                                        // 排序号
	private int parentId;                                  // 父分类ID
	private Date createDate;                            // 创建时间
	private Date updateDate;                          // 更新时间
	private List<Category> subCategoryList;   // 子分类列表

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
