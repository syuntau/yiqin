package com.yiqin.shop.bean;

import com.yiqin.util.Util;

public class Monitor extends ProductBase {

	private static final long serialVersionUID = -4813890355050904011L;
	private String pingMuChiCun;              // 屏幕尺寸
	private String fenBianLv;                 // 分辨率
	private String mianBanLeiXing;            // 面板类型
	private String kuanPing;                  // 是否宽屏
	private String pingMuBiLi;                // 屏幕比例
	private String xiangYingShiJian;          // 响应时间
	private String xiangYingFanWei;           // 响应范围
	private String dianJu;                    // 点距
	private String seCha;                     // 色差
	private String liangDu;                   // 亮度
	private String duiBiDu;                   // 对比度
	private String keShiJiaoDu;               // 可视角度
	private String neiZhiYinXiang;            // 内置音箱
	private String hdcp;                      // HDCP
	private String led;                       // 是否led
	private String jieKou;                    // 接口
	private String dianYuan;                  // 电源
	private String biGua;                     // 壁挂支持
	private String biGuaGuiGe;                // 壁挂规格
	private String diZuo;                     // 底座
	private String teXing;                    // 特性
	public String getPingMuChiCun() {
		return pingMuChiCun;
	}
	public void setPingMuChiCun(String pingMuChiCun) {
		this.pingMuChiCun = pingMuChiCun;
	}
	public String getFenBianLv() {
		return fenBianLv;
	}
	public void setFenBianLv(String fenBianLv) {
		this.fenBianLv = fenBianLv;
	}
	public String getMianBanLeiXing() {
		return mianBanLeiXing;
	}
	public void setMianBanLeiXing(String mianBanLeiXing) {
		this.mianBanLeiXing = mianBanLeiXing;
	}
	public String getKuanPing() {
		return kuanPing;
	}
	public void setKuanPing(String kuanPing) {
		this.kuanPing = kuanPing;
	}
	public String getPingMuBiLi() {
		return pingMuBiLi;
	}
	public void setPingMuBiLi(String pingMuBiLi) {
		this.pingMuBiLi = pingMuBiLi;
	}
	public String getXiangYingShiJian() {
		return xiangYingShiJian;
	}
	public void setXiangYingShiJian(String xiangYingShiJian) {
		this.xiangYingShiJian = xiangYingShiJian;
	}
	public String getXiangYingFanWei() {
		return xiangYingFanWei;
	}
	public void setXiangYingFanWei(String xiangYingFanWei) {
		this.xiangYingFanWei = xiangYingFanWei;
	}
	public String getDianJu() {
		return dianJu;
	}
	public void setDianJu(String dianJu) {
		this.dianJu = dianJu;
	}
	public String getSeCha() {
		return seCha;
	}
	public void setSeCha(String seCha) {
		this.seCha = seCha;
	}
	public String getLiangDu() {
		return liangDu;
	}
	public void setLiangDu(String liangDu) {
		this.liangDu = liangDu;
	}
	public String getDuiBiDu() {
		return duiBiDu;
	}
	public void setDuiBiDu(String duiBiDu) {
		this.duiBiDu = duiBiDu;
	}
	public String getKeShiJiaoDu() {
		return keShiJiaoDu;
	}
	public void setKeShiJiaoDu(String keShiJiaoDu) {
		this.keShiJiaoDu = keShiJiaoDu;
	}
	public String getNeiZhiYinXiang() {
		return neiZhiYinXiang;
	}
	public void setNeiZhiYinXiang(String neiZhiYinXiang) {
		this.neiZhiYinXiang = neiZhiYinXiang;
	}
	public String getHdcp() {
		return hdcp;
	}
	public void setHdcp(String hdcp) {
		this.hdcp = hdcp;
	}
	public String getLed() {
		return led;
	}
	public void setLed(String led) {
		this.led = led;
	}
	public String getJieKou() {
		return jieKou;
	}
	public void setJieKou(String jieKou) {
		this.jieKou = jieKou;
	}
	public String getDianYuan() {
		return dianYuan;
	}
	public void setDianYuan(String dianYuan) {
		this.dianYuan = dianYuan;
	}
	public String getBiGua() {
		return biGua;
	}
	public void setBiGua(String biGua) {
		this.biGua = biGua;
	}
	public String getBiGuaGuiGe() {
		return biGuaGuiGe;
	}
	public void setBiGuaGuiGe(String biGuaGuiGe) {
		this.biGuaGuiGe = biGuaGuiGe;
	}
	public String getDiZuo() {
		return diZuo;
	}
	public void setDiZuo(String diZuo) {
		this.diZuo = diZuo;
	}
	public String getTeXing() {
		return teXing;
	}
	public void setTeXing(String teXing) {
		this.teXing = teXing;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
