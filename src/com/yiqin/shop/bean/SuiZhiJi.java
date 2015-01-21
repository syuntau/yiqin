package com.yiqin.shop.bean;

import com.yiqin.util.Util;

public class SuiZhiJi extends ProductBase {

	private static final long serialVersionUID = 2418369998420540202L;
	private String dianYuan;                   // 电源
	private String gongLv;                     // 功率

	// 碎纸机
	private String suiZhiNengLi;               // 碎纸能力/碎纸量
	private String baoMiDengJi;                // 保密等级
	private String keSuiCaiZhi;                // 可碎材质
	private String suiZhiSuDu;                 // 碎纸速度
	private String suiZhiXiaoGuo;              // 碎纸效果
	private String zhiTongXiangRongLiang;      // 纸筒箱容量
	private String zaoYin;                     // 噪音
	private String lianXuGongZuoShiJian;       // 连续工作时间

	// 装订机
	private String zhuangDingHaoCai;           // 装订耗材
	private String zhuangDingCaiLiao;          // 装订材料
	private String zhuangDingHouDu;            // 装订厚度
	private String daKongHouDu;                // 打孔厚度
	private String daKongZhiJing;              // 打孔直径
	private String gongZuoTaiChiCun;           // 工作台尺寸
	private String zuanTou;                    // 钻头
	private String yuReShiJian;                // 预热时间
	private String zhuangDingShiJian;          // 装订时间
	private String zhiZhangGuiGe;              // 装订规格
	public String getDianYuan() {
		return dianYuan;
	}
	public void setDianYuan(String dianYuan) {
		this.dianYuan = dianYuan;
	}
	public String getGongLv() {
		return gongLv;
	}
	public void setGongLv(String gongLv) {
		this.gongLv = gongLv;
	}
	public String getSuiZhiNengLi() {
		return suiZhiNengLi;
	}
	public void setSuiZhiNengLi(String suiZhiNengLi) {
		this.suiZhiNengLi = suiZhiNengLi;
	}
	public String getBaoMiDengJi() {
		return baoMiDengJi;
	}
	public void setBaoMiDengJi(String baoMiDengJi) {
		this.baoMiDengJi = baoMiDengJi;
	}
	public String getKeSuiCaiZhi() {
		return keSuiCaiZhi;
	}
	public void setKeSuiCaiZhi(String keSuiCaiZhi) {
		this.keSuiCaiZhi = keSuiCaiZhi;
	}
	public String getSuiZhiSuDu() {
		return suiZhiSuDu;
	}
	public void setSuiZhiSuDu(String suiZhiSuDu) {
		this.suiZhiSuDu = suiZhiSuDu;
	}
	public String getSuiZhiXiaoGuo() {
		return suiZhiXiaoGuo;
	}
	public void setSuiZhiXiaoGuo(String suiZhiXiaoGuo) {
		this.suiZhiXiaoGuo = suiZhiXiaoGuo;
	}
	public String getZhiTongXiangRongLiang() {
		return zhiTongXiangRongLiang;
	}
	public void setZhiTongXiangRongLiang(String zhiTongXiangRongLiang) {
		this.zhiTongXiangRongLiang = zhiTongXiangRongLiang;
	}
	public String getZaoYin() {
		return zaoYin;
	}
	public void setZaoYin(String zaoYin) {
		this.zaoYin = zaoYin;
	}
	public String getLianXuGongZuoShiJian() {
		return lianXuGongZuoShiJian;
	}
	public void setLianXuGongZuoShiJian(String lianXuGongZuoShiJian) {
		this.lianXuGongZuoShiJian = lianXuGongZuoShiJian;
	}
	public String getZhuangDingHaoCai() {
		return zhuangDingHaoCai;
	}
	public void setZhuangDingHaoCai(String zhuangDingHaoCai) {
		this.zhuangDingHaoCai = zhuangDingHaoCai;
	}
	public String getZhuangDingCaiLiao() {
		return zhuangDingCaiLiao;
	}
	public void setZhuangDingCaiLiao(String zhuangDingCaiLiao) {
		this.zhuangDingCaiLiao = zhuangDingCaiLiao;
	}
	public String getZhuangDingHouDu() {
		return zhuangDingHouDu;
	}
	public void setZhuangDingHouDu(String zhuangDingHouDu) {
		this.zhuangDingHouDu = zhuangDingHouDu;
	}
	public String getDaKongHouDu() {
		return daKongHouDu;
	}
	public void setDaKongHouDu(String daKongHouDu) {
		this.daKongHouDu = daKongHouDu;
	}
	public String getDaKongZhiJing() {
		return daKongZhiJing;
	}
	public void setDaKongZhiJing(String daKongZhiJing) {
		this.daKongZhiJing = daKongZhiJing;
	}
	public String getGongZuoTaiChiCun() {
		return gongZuoTaiChiCun;
	}
	public void setGongZuoTaiChiCun(String gongZuoTaiChiCun) {
		this.gongZuoTaiChiCun = gongZuoTaiChiCun;
	}
	public String getZuanTou() {
		return zuanTou;
	}
	public void setZuanTou(String zuanTou) {
		this.zuanTou = zuanTou;
	}
	public String getYuReShiJian() {
		return yuReShiJian;
	}
	public void setYuReShiJian(String yuReShiJian) {
		this.yuReShiJian = yuReShiJian;
	}
	public String getZhuangDingShiJian() {
		return zhuangDingShiJian;
	}
	public void setZhuangDingShiJian(String zhuangDingShiJian) {
		this.zhuangDingShiJian = zhuangDingShiJian;
	}
	public String getZhiZhangGuiGe() {
		return zhiZhangGuiGe;
	}
	public void setZhiZhangGuiGe(String zhiZhangGuiGe) {
		this.zhiZhangGuiGe = zhiZhangGuiGe;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
