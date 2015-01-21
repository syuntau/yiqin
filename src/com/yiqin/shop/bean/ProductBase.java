package com.yiqin.shop.bean;

import java.io.Serializable;
import java.util.Date;

import com.yiqin.util.Util;

public class ProductBase implements Serializable {

	private static final long serialVersionUID = -9214719610872343839L;
	private String id;                     // 产品ID
	private String name;                   // 产品名称
	private String brandId;                // 产品厂商
	private String category;               // 商城产品大类，办公用品、礼品卡、体育用品等
	private String category1;              // 第一级分类：产品大类下第一级分类
	private String category2;              // 第二级分类：产品大类下第二季分类
	private String category3;              // 第三级分类：产品大类下第三级分类
	private float price;                   // 价格
	private float discount;                // 统一折扣 
	private float userDiscount;            // 用户特别折扣
	private String imageUrl;               // 商品图片，多个地址用 逗号 分隔
	private String madeIn;                 // 产品产地
	private String comment;                // 产品备注
	private String packageList;            // 包装清单
	private Date saleDate;                 // 上架时间
	private String afterMarketTel;         // 售后电话
	private String homePage;               // 官网
	private String warranty;               // 保修
	private Date createDate;               // 产品创建时间
	private Date updateDate;               // 产品修改时间
	private int deleteFlag;                // 产品删除Flag

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getCategory1() {
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getCategory2() {
		return category2;
	}
	public void setCategory2(String category2) {
		this.category2 = category2;
	}
	public String getCategory3() {
		return category3;
	}
	public void setCategory3(String category3) {
		this.category3 = category3;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public float getUserDiscount() {
		return userDiscount;
	}
	public void setUserDiscount(float userDiscount) {
		this.userDiscount = userDiscount;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMadeIn() {
		return madeIn;
	}
	public void setMadeIn(String madeIn) {
		this.madeIn = madeIn;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getPackageList() {
		return packageList;
	}
	public void setPackageList(String packageList) {
		this.packageList = packageList;
	}
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	public String getAfterMarketTel() {
		return afterMarketTel;
	}
	public void setAfterMarketTel(String afterMarketTel) {
		this.afterMarketTel = afterMarketTel;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public String getWarranty() {
		return warranty;
	}
	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
