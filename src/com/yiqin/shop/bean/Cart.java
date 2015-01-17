package com.yiqin.shop.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车
 * 
 * @author LiuJun
 * 
 */
public class Cart implements Serializable {
	private static final long serialVersionUID = -7350288413048538777L;
	// 用户名
	private String name;
	// 商品ID
	private String productId;
	// 商品缩略图URL
	private String imageUrl;
	// 数量
	private int productNum;
	// 商品名
	private String productName;
	// 单价
	private float price;
	// 创建时间
	private Date createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
