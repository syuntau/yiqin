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
	private String userId;             // 用户ID
	private String name;               // 用户名
	private String address;            // 送货地址
	private String phone;              // 用户电话
	private String email;              // 用户Email
	private int zhiFu;                 // 支付方式
	private float yunFei;              // 运费
	private String songHuoRiQi;        // 送货日期
	private String songhuoShiJian;     // 送货时间
	private String faPiaoTaiTou;       // 发票抬头
	private String faPiaoMingXi;       // 发票明细
	private String productList;        // 商品ID + 商品缩略图URL + 商品名 + 数量  + 单价 + 总价
	private float yuanJia;             // 订单原价
	private float zheKou;              // 订单折扣
	private float zongJia;             // 订单总价
	private Date createDate;           // 订单创建时间
	private Date updateDate;           // 订单修改时间
	private int deleteFlag;            // 删除Flag
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getZhiFu() {
		return zhiFu;
	}
	public void setZhiFu(int zhiFu) {
		this.zhiFu = zhiFu;
	}
	public float getYunFei() {
		return yunFei;
	}
	public void setYunFei(float yunFei) {
		this.yunFei = yunFei;
	}
	public String getSongHuoRiQi() {
		return songHuoRiQi;
	}
	public void setSongHuoRiQi(String songHuoRiQi) {
		this.songHuoRiQi = songHuoRiQi;
	}
	public String getSonghuoShiJian() {
		return songhuoShiJian;
	}
	public void setSonghuoShiJian(String songhuoShiJian) {
		this.songhuoShiJian = songhuoShiJian;
	}
	public String getFaPiaoTaiTou() {
		return faPiaoTaiTou;
	}
	public void setFaPiaoTaiTou(String faPiaoTaiTou) {
		this.faPiaoTaiTou = faPiaoTaiTou;
	}
	public String getFaPiaoMingXi() {
		return faPiaoMingXi;
	}
	public void setFaPiaoMingXi(String faPiaoMingXi) {
		this.faPiaoMingXi = faPiaoMingXi;
	}
	public String getProductList() {
		return productList;
	}
	public void setProductList(String productList) {
		this.productList = productList;
	}
	public float getYuanJia() {
		return yuanJia;
	}
	public void setYuanJia(float yuanJia) {
		this.yuanJia = yuanJia;
	}
	public float getZheKou() {
		return zheKou;
	}
	public void setZheKou(float zheKou) {
		this.zheKou = zheKou;
	}
	public float getZongJia() {
		return zongJia;
	}
	public void setZongJia(float zongJia) {
		this.zongJia = zongJia;
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
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
