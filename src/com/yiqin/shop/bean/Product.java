package com.yiqin.shop.bean;

import java.io.Serializable;

import com.yiqin.util.Util;

public class Product implements Serializable {

	private static final long serialVersionUID = 8713123401386764517L;
	private int id; // 自增主键
	private String product_id; // 产品ID
	private int attribute_id; // 属性ID
	private String value; // 属性值

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
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

	public int getAttribute_id() {
		return attribute_id;
	}

	public void setAttribute_id(int attribute_id) {
		this.attribute_id = attribute_id;
	}

}
