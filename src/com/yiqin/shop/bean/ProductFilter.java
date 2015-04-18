package com.yiqin.shop.bean;

/**
 * 商品过滤参数类
 * 
 * @author LiuJun
 * 
 */
public class ProductFilter {
	// 分类ID
	private String categorys;
	// 过滤品牌
	private String brand;
	// 过滤价格
	private String price;
	// 过滤颜色
	private String color;
	// 第一条记录索引
	private int offset;
	// 每页显示的条目数目
	private int pageSize;

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
