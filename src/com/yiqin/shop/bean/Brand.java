package com.yiqin.shop.bean;

import java.io.Serializable;

import com.yiqin.util.Util;

public class Brand implements Serializable {

	private static final long serialVersionUID = 8587193569104000532L;
	private int id; // 品牌ID
	private String name_en; // 品牌英文名
	private String name_cn; // 品牌中文名

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName_cn() {
		return name_cn;
	}

	public void setName_cn(String name_cn) {
		this.name_cn = name_cn;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
