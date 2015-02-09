package com.yiqin.shop.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yiqin.util.Util;

public class Category implements Serializable {

	private static final long serialVersionUID = 6531351367406306429L;
	private int id; // 分类ID
	private String name; // 名称
	private int level; // 排序号
	private int parent_id; // 父分类ID
	private Date create_date; // 创建时间
	private Date update_date; // 更新时间
	private List<Category> subCategoryList; // 子分类列表

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

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
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
