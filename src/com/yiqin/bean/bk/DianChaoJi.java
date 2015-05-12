package com.yiqin.bean.bk;

import com.yiqin.util.Util;

public class DianChaoJi extends ProductBase {

	private static final long serialVersionUID = -98614845859068410L;
	private String dianYa;                 // 电压
	private String gongLv;                 // 功率
	private String dianChaoSuDu;           // 点钞速度
	private String mianBan;                // 面板
	private String jieKou;                 // 接口
	private String xianShi;                // 显示
	private String jianWeiFangShi;         // 鉴伪方式
	private String jiShuFanWei;            // 计数范围
	private String huanJingWenDu;          // 环境温度
	private String shiYongGongNeng;        // 使用功能
	public String getDianYa() {
		return dianYa;
	}
	public void setDianYa(String dianYa) {
		this.dianYa = dianYa;
	}
	public String getGongLv() {
		return gongLv;
	}
	public void setGongLv(String gongLv) {
		this.gongLv = gongLv;
	}
	public String getDianChaoSuDu() {
		return dianChaoSuDu;
	}
	public void setDianChaoSuDu(String dianChaoSuDu) {
		this.dianChaoSuDu = dianChaoSuDu;
	}
	public String getMianBan() {
		return mianBan;
	}
	public void setMianBan(String mianBan) {
		this.mianBan = mianBan;
	}
	public String getJieKou() {
		return jieKou;
	}
	public void setJieKou(String jieKou) {
		this.jieKou = jieKou;
	}
	public String getXianShi() {
		return xianShi;
	}
	public void setXianShi(String xianShi) {
		this.xianShi = xianShi;
	}
	public String getJianWeiFangShi() {
		return jianWeiFangShi;
	}
	public void setJianWeiFangShi(String jianWeiFangShi) {
		this.jianWeiFangShi = jianWeiFangShi;
	}
	public String getJiShuFanWei() {
		return jiShuFanWei;
	}
	public void setJiShuFanWei(String jiShuFanWei) {
		this.jiShuFanWei = jiShuFanWei;
	}
	public String getHuanJingWenDu() {
		return huanJingWenDu;
	}
	public void setHuanJingWenDu(String huanJingWenDu) {
		this.huanJingWenDu = huanJingWenDu;
	}
	public String getShiYongGongNeng() {
		return shiYongGongNeng;
	}
	public void setShiYongGongNeng(String shiYongGongNeng) {
		this.shiYongGongNeng = shiYongGongNeng;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
