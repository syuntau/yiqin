package com.yiqin.shop.bean;

import java.io.Serializable;

import com.yiqin.util.Util;

public class CategoryCong implements Serializable {

	private static final long serialVersionUID = 2285787835054379372L;
	private int id; // 自增主键
	private int category_id; // 分类ID
	private String attribute; // 分类配置属性
	private String value; // 分类配置属性值

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
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
