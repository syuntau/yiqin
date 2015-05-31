package com.yiqin.shop.bean;

/**
 * 用于产品列表展示
 * 
 * @author LiuJun
 * 
 */
public class ProductView {
	private String productId;// 产品ID
	private String productName; // 名称
	private String price; // 价格
	private String zhekouPrice;//折扣价格
	private String color;// 颜色
	private String imgUrl; // 图片链接

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getZhekouPrice() {
		return zhekouPrice;
	}

	public void setZhekouPrice(String zhekouPrice) {
		this.zhekouPrice = zhekouPrice;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
