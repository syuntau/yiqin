package com.yiqin.pojo;

// Generated 2015-2-12 14:36:12 by Hibernate Tools 3.4.0.CR1

/**
 * Brand generated by hbm2java
 */
public class Brand implements java.io.Serializable {

	private Integer id;// 品牌ID
	private String nameEn;// 品牌英文名
	private String nameCn;// 品牌中文名

	public Brand() {
	}

	public Brand(String nameEn, String nameCn) {
		this.nameEn = nameEn;
		this.nameCn = nameCn;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameEn() {
		return this.nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameCn() {
		return this.nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

}