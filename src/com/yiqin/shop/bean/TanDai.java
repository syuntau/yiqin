package com.yiqin.shop.bean;

import com.yiqin.util.Util;

public class TanDai extends ProductBase {

	private static final long serialVersionUID = 6579787096840752365L;
	private String shiYongJiXing;                  // 适用机型
	private String daYinLiang;                     // 打印量
	private String changDu;                        // 长度
	private String shiYongJieZhi;                  // 适用介质
	private String shiYongHuanJing;                // 使用环境
	private String guiGe;                          // 规格
	private String zhouXinChiCun;                  // 轴心尺寸
	public String getShiYongJiXing() {
		return shiYongJiXing;
	}
	public void setShiYongJiXing(String shiYongJiXing) {
		this.shiYongJiXing = shiYongJiXing;
	}
	public String getDaYinLiang() {
		return daYinLiang;
	}
	public void setDaYinLiang(String daYinLiang) {
		this.daYinLiang = daYinLiang;
	}
	public String getChangDu() {
		return changDu;
	}
	public void setChangDu(String changDu) {
		this.changDu = changDu;
	}
	public String getShiYongJieZhi() {
		return shiYongJieZhi;
	}
	public void setShiYongJieZhi(String shiYongJieZhi) {
		this.shiYongJieZhi = shiYongJieZhi;
	}
	public String getShiYongHuanJing() {
		return shiYongHuanJing;
	}
	public void setShiYongHuanJing(String shiYongHuanJing) {
		this.shiYongHuanJing = shiYongHuanJing;
	}
	public String getGuiGe() {
		return guiGe;
	}
	public void setGuiGe(String guiGe) {
		this.guiGe = guiGe;
	}
	public String getZhouXinChiCun() {
		return zhouXinChiCun;
	}
	public void setZhouXinChiCun(String zhouXinChiCun) {
		this.zhouXinChiCun = zhouXinChiCun;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
