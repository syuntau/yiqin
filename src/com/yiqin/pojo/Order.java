package com.yiqin.pojo;

// Generated 2015-2-12 14:36:12 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Order generated by hbm2java
 */
public class Order implements java.io.Serializable {

	private static final long serialVersionUID = 4005154186169390676L;
	private long id;// 订单号
	private Byte status;// 订单状态
	private String userId;// 用户ID
	private String name; // 用户名
	private String address;// 送货地址
	private String mobile;// 用户电话
	private String email;// 用户Email
	private Byte zhifu;// 支付方式
	private Float yunfei; // 运费
	private String songhuoriqi;// 送货日期
	private String peisongfangshi;// 配送方式
	private String fapiaolx;//发票类型
	private String fapiaotaitou;// 发票抬头
	private String fapiaomingxi;// 发票明细
	private String productList; // cart对象list 
	private String yuanjia;// 订单原价
	private Float zhekou;// 订单折扣
	private String zongjia;// 订单总价
	private Date crateDate;// 订单创建时间
	private Date updateDate;// 订单修改时间
	private Byte deleteFlag; // 删除Flag
	private String orderNote;// 订单备注
	private String beizhuzongjia;// 备注总价

	public Order() {
	}

	public Order(long id) {
		this.id = id;
	}

	public Order(long id, Byte status, String userId, String name,
			String address, String mobile, String email, Byte zhifu,
			Float yunfei, String songhuoriqi, String peisongfangshi,String fapiaolx,
			String fapiaotaitou, String fapiaomingxi, String productList,
			String yuanjia, Float zhekou, String zongjia, Date crateDate,
			Date updateDate, Byte deleteFlag, String orderNote, String beizhuzongjia) {
		this.id = id;
		this.status = status;
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.mobile = mobile;
		this.email = email;
		this.zhifu = zhifu;
		this.yunfei = yunfei;
		this.songhuoriqi = songhuoriqi;
		this.peisongfangshi = peisongfangshi;
		this.fapiaolx = fapiaolx;
		this.fapiaotaitou = fapiaotaitou;
		this.fapiaomingxi = fapiaomingxi;
		this.productList = productList;
		this.yuanjia = yuanjia;
		this.zhekou = zhekou;
		this.zongjia = zongjia;
		this.crateDate = crateDate;
		this.updateDate = updateDate;
		this.deleteFlag = deleteFlag;
		this.orderNote = orderNote;
		this.beizhuzongjia = beizhuzongjia;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Byte getZhifu() {
		return this.zhifu;
	}

	public void setZhifu(Byte zhifu) {
		this.zhifu = zhifu;
	}

	public Float getYunfei() {
		return this.yunfei;
	}

	public void setYunfei(Float yunfei) {
		this.yunfei = yunfei;
	}

	public String getSonghuoriqi() {
		return this.songhuoriqi;
	}

	public void setSonghuoriqi(String songhuoriqi) {
		this.songhuoriqi = songhuoriqi;
	}

	public String getPeisongfangshi() {
		return peisongfangshi;
	}

	public void setPeisongfangshi(String peisongfangshi) {
		this.peisongfangshi = peisongfangshi;
	}

	public String getFapiaotaitou() {
		return this.fapiaotaitou;
	}

	public String getFapiaolx() {
		return fapiaolx;
	}

	public void setFapiaolx(String fapiaolx) {
		this.fapiaolx = fapiaolx;
	}

	public void setFapiaotaitou(String fapiaotaitou) {
		this.fapiaotaitou = fapiaotaitou;
	}

	public String getFapiaomingxi() {
		return this.fapiaomingxi;
	}

	public void setFapiaomingxi(String fapiaomingxi) {
		this.fapiaomingxi = fapiaomingxi;
	}

	public String getProductList() {
		return this.productList;
	}

	public void setProductList(String productList) {
		this.productList = productList;
	}

	public String getYuanjia() {
		return this.yuanjia;
	}

	public void setYuanjia(String yuanjia) {
		this.yuanjia = yuanjia;
	}

	public Float getZhekou() {
		return this.zhekou;
	}

	public void setZhekou(Float zhekou) {
		this.zhekou = zhekou;
	}

	public String getZongjia() {
		return this.zongjia;
	}

	public void setZongjia(String zongjia) {
		this.zongjia = zongjia;
	}

	public Date getCrateDate() {
		return this.crateDate;
	}

	public void setCrateDate(Date crateDate) {
		this.crateDate = crateDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Byte getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Byte deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public String getBeizhuzongjia() {
		return beizhuzongjia;
	}

	public void setBeizhuzongjia(String beizhuzongjia) {
		this.beizhuzongjia = beizhuzongjia;
	}

}
