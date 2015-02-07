package com.yiqin.shop.bean;

import java.io.Serializable;

import com.yiqin.util.Util;

public class Brand implements Serializable {

	private static final long serialVersionUID = 8587193569104000532L;
	private int id;                         // 品牌ID
	private String nameEn;          // 品牌英文名
	private String nameCn;          // 品牌中文名

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameCn() {
		return nameCn;
	}
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	public String toString() {
		return Util.objToString(this);
	}
}
