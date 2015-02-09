package com.yiqin.shop.bean;

import java.io.Serializable;

import com.yiqin.util.Util;

public class Attribute implements Serializable {

	private static final long serialVersionUID = 5900510729249709196L;
	private int id; // 属性ID，自增主键
	private String name_id; // 属性标识符，例如：价格 为 price
	private String name; // 属性名称
	private String value; // 属性值（可能是包含多个值，例如：颜色 属性 的值为：黑色,红色,蓝色,白色）
	private int category_id; // 隶属的 二级分类ID，如果设定为 0，则为各分类通用属性
	private int filter; // 是否为 二级分类 下可以筛选属性，0：不筛选，1：筛选
	private int filter_type; // 筛选类型，0：无类型，1：组合型，2：价格型，3：连续型
	private String show_value; // 筛选显示值，筛选类型为1时，等同于 value 属性，筛选类型为2、3时，客户设定
	private int sort; // 是否 支持 排序，0：不排序，1：排序

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName_id() {
		return name_id;
	}

	public void setName_id(String name_id) {
		this.name_id = name_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getFilter() {
		return filter;
	}

	public void setFilter(int filter) {
		this.filter = filter;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getFilter_type() {
		return filter_type;
	}

	public void setFilter_type(int filter_type) {
		this.filter_type = filter_type;
	}

	public String getShow_value() {
		return show_value;
	}

	public void setShow_value(String show_value) {
		this.show_value = show_value;
	}

	public String toString() {
		return Util.objToString(this);
	}

}
