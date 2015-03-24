package com.yiqin.shop.bean;

import java.util.List;

import com.yiqin.shop.pojo.Cart;

/**
 * 订单详情、列表展示
 * 
 * @author liujun
 *
 */
public class OrderView {
	private long id;// 订单号
	private String status;// 订单状态
	private String userId;// 用户ID
	private String name; // 用户名
	private String address;// 送货地址
	private String mobile;// 用户电话
	private String email;// 用户Email
	private Byte zhifu;// 支付方式
	private Float yunfei; // 运费
	private String songhuoriqi;// 送货日期
	private String peisongfangshi;// 配送方式
	private String fapiaotaitou;// 发票抬头
	private String fapiaomingxi;// 发票明细
	private List<Cart> productList; // 商品记录
	private Float yuanjia;// 订单原价
	private Float zhekou;// 订单折扣
	private Float zongjia;// 订单总价
	private String crateDate;// 订单创建时间
	private String updateDate;// 订单修改时间
	private Byte deleteFlag; // 删除Flag

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Byte getZhifu() {
		return zhifu;
	}

	public void setZhifu(Byte zhifu) {
		this.zhifu = zhifu;
	}

	public Float getYunfei() {
		return yunfei;
	}

	public void setYunfei(Float yunfei) {
		this.yunfei = yunfei;
	}

	public String getSonghuoriqi() {
		return songhuoriqi;
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
		return fapiaotaitou;
	}

	public void setFapiaotaitou(String fapiaotaitou) {
		this.fapiaotaitou = fapiaotaitou;
	}

	public String getFapiaomingxi() {
		return fapiaomingxi;
	}

	public void setFapiaomingxi(String fapiaomingxi) {
		this.fapiaomingxi = fapiaomingxi;
	}

	public List<Cart> getProductList() {
		return productList;
	}

	public void setProductList(List<Cart> productList) {
		this.productList = productList;
	}

	public Float getYuanjia() {
		return yuanjia;
	}

	public void setYuanjia(Float yuanjia) {
		this.yuanjia = yuanjia;
	}

	public Float getZhekou() {
		return zhekou;
	}

	public void setZhekou(Float zhekou) {
		this.zhekou = zhekou;
	}

	public Float getZongjia() {
		return zongjia;
	}

	public void setZongjia(Float zongjia) {
		this.zongjia = zongjia;
	}

	public String getCrateDate() {
		return crateDate;
	}

	public void setCrateDate(String crateDate) {
		this.crateDate = crateDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Byte getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Byte deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
