package com.yiqin.shop.bean;

import java.io.Serializable;

import com.yiqin.util.Util;

public class CategoryCong implements Serializable {

	private static final long serialVersionUID = 2285787835054379372L;
	private int id;                              // 自增主键
	private int categoryId;                // 分类ID
	private String attribute;              // 分类配置属性
	private String value;                   // 分类配置属性值
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
