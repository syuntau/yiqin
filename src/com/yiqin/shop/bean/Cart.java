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
	private int id;// 自增主键
	private String use_id; // 用户名
	private String product_id; // 商品ID
	private String img_url; // 商品缩略图URL
	private int count; // 数量
	private String product_name; // 商品名
	private float price; // 单价
	private String product_info; // 商品其他属性
	private Date createDate; // 创建时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUse_id() {
		return use_id;
	}

	public void setUse_id(String use_id) {
		this.use_id = use_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_info() {
		return product_info;
	}

	public void setProduct_info(String product_info) {
		this.product_info = product_info;
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
