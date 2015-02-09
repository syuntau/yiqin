package com.yiqin.shop.bean;

import java.io.Serializable;
import java.util.Date;

import com.yiqin.util.Util;

/**
 * 订单信息
 * 
 * @author tao
 * 
 */
public class Order implements Serializable {

	private static final long serialVersionUID = -6834642753693933268L;
	private long id;                   // 订单号
	private int status;                // 订单状态
	private String user_id;             // 用户ID
	private String name;               // 用户名
	private String address;            // 送货地址
	private String mobile;              // 用户电话
	private String email;              // 用户Email
	private int zhifu;                 // 支付方式
	private float yunfei;              // 运费
	private String songhuoriqi;        // 送货日期
	private String songhuoshijian;     // 送货时间
	private String fapiaotaitou;       // 发票抬头
	private String fapiaomingxi;       // 发票明细
	private String product_list;        // 商品ID + 商品缩略图URL + 商品名 + 数量  + 单价 + 总价
	private float yuanjia;             // 订单原价
	private float zhekou;              // 订单折扣
	private float zongjia;             // 订单总价
	private Date crate_date;           // 订单创建时间
	private Date update_date;           // 订单修改时间
	private int delete_flag;            // 删除Flag

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public int getZhifu() {
		return zhifu;
	}

	public void setZhifu(int zhifu) {
		this.zhifu = zhifu;
	}

	public float getYunfei() {
		return yunfei;
	}

	public void setYunfei(float yunfei) {
		this.yunfei = yunfei;
	}

	public String getSonghuoriqi() {
		return songhuoriqi;
	}

	public void setSonghuoriqi(String songhuoriqi) {
		this.songhuoriqi = songhuoriqi;
	}

	public String getSonghuoshijian() {
		return songhuoshijian;
	}

	public void setSonghuoshijian(String songhuoshijian) {
		this.songhuoshijian = songhuoshijian;
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

	public String getProduct_list() {
		return product_list;
	}

	public void setProduct_list(String product_list) {
		this.product_list = product_list;
	}

	public float getYuanjia() {
		return yuanjia;
	}

	public void setYuanjia(float yuanjia) {
		this.yuanjia = yuanjia;
	}

	public float getZhekou() {
		return zhekou;
	}

	public void setZhekou(float zhekou) {
		this.zhekou = zhekou;
	}

	public float getZongjia() {
		return zongjia;
	}

	public void setZongjia(float zongjia) {
		this.zongjia = zongjia;
	}

	public Date getCrate_date() {
		return crate_date;
	}

	public void setCrate_date(Date crate_date) {
		this.crate_date = crate_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
