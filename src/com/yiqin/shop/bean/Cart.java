package com.yiqin.shop.bean;

import java.io.Serializable;
import java.util.Date;

import com.yiqin.util.Util;

/**
 * 购物车
 * 
 * @author LiuJun
 * 
 */
public class Cart implements Serializable {
	private static final long serialVersionUID = -7350288413048538777L;
	private String userId;                      // 用户名
	private String productId;                // 商品ID
	private String imageUrl;                 // 商品缩略图URL
	private int productNum;                // 数量
	private String productName;         // 商品名
	private float price;                        // 单价
	private Date createDate;               // 创建时间

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String toString() {
		return Util.objToString(this);
	}
}
