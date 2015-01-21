package com.yiqin.shop.bean;

import com.yiqin.util.Util;

public class Router extends ProductBase {

	private static final long serialVersionUID = 8822757998422785706L;
	private String jieKou;                      // 接口/端口
	private String shiYongHuanJing;             // 使用环境：温度、湿度等
	private String xieYi;                       // 协议
	private String dianYuan;                    // 电源
	private String zhuangTaiZhiShiDeng;         // 状态指示灯

	// 路由器
	private String wuXianSuLv;                  // 无线速率
	private String anNiu;                       // 按钮
	private String tianXian;                    // 天线
	private String jiBenGongNeng;               // 基本功能
	private String wuXianAnQuan;                // 无线安全
	private String shiYongGongNeng;             // 使用功能
	private String wangLuoSheZhi;               // 网络设置
	private String dhcp;                        // DHCP
	private String duanKouZhuanFa;              // 端口转发
	private String anQuanSheZhi;                // 安全设置
	private String shangWangKongZhi;            // 上网控制
	private String xiTongGongJu;                // 系统工具

	// 交换机
	private String wangLuoBiaoZhun;             // 网络标准
	private String tuoPuJieGou;                 // 拓扑结构
	private String wangLuoLeiXing;              // 网络类型
	private String jiaoHuanFangShi;             // 交换方式
	private String zhuanFaSuLv;                 // 转发速率
	private String daiKuan;                     // 带宽
	public String getJieKou() {
		return jieKou;
	}
	public void setJieKou(String jieKou) {
		this.jieKou = jieKou;
	}
	public String getShiYongHuanJing() {
		return shiYongHuanJing;
	}
	public void setShiYongHuanJing(String shiYongHuanJing) {
		this.shiYongHuanJing = shiYongHuanJing;
	}
	public String getXieYi() {
		return xieYi;
	}
	public void setXieYi(String xieYi) {
		this.xieYi = xieYi;
	}
	public String getDianYuan() {
		return dianYuan;
	}
	public void setDianYuan(String dianYuan) {
		this.dianYuan = dianYuan;
	}
	public String getZhuangTaiZhiShiDeng() {
		return zhuangTaiZhiShiDeng;
	}
	public void setZhuangTaiZhiShiDeng(String zhuangTaiZhiShiDeng) {
		this.zhuangTaiZhiShiDeng = zhuangTaiZhiShiDeng;
	}
	public String getWuXianSuLv() {
		return wuXianSuLv;
	}
	public void setWuXianSuLv(String wuXianSuLv) {
		this.wuXianSuLv = wuXianSuLv;
	}
	public String getAnNiu() {
		return anNiu;
	}
	public void setAnNiu(String anNiu) {
		this.anNiu = anNiu;
	}
	public String getTianXian() {
		return tianXian;
	}
	public void setTianXian(String tianXian) {
		this.tianXian = tianXian;
	}
	public String getJiBenGongNeng() {
		return jiBenGongNeng;
	}
	public void setJiBenGongNeng(String jiBenGongNeng) {
		this.jiBenGongNeng = jiBenGongNeng;
	}
	public String getWuXianAnQuan() {
		return wuXianAnQuan;
	}
	public void setWuXianAnQuan(String wuXianAnQuan) {
		this.wuXianAnQuan = wuXianAnQuan;
	}
	public String getShiYongGongNeng() {
		return shiYongGongNeng;
	}
	public void setShiYongGongNeng(String shiYongGongNeng) {
		this.shiYongGongNeng = shiYongGongNeng;
	}
	public String getWangLuoSheZhi() {
		return wangLuoSheZhi;
	}
	public void setWangLuoSheZhi(String wangLuoSheZhi) {
		this.wangLuoSheZhi = wangLuoSheZhi;
	}
	public String getDhcp() {
		return dhcp;
	}
	public void setDhcp(String dhcp) {
		this.dhcp = dhcp;
	}
	public String getDuanKouZhuanFa() {
		return duanKouZhuanFa;
	}
	public void setDuanKouZhuanFa(String duanKouZhuanFa) {
		this.duanKouZhuanFa = duanKouZhuanFa;
	}
	public String getAnQuanSheZhi() {
		return anQuanSheZhi;
	}
	public void setAnQuanSheZhi(String anQuanSheZhi) {
		this.anQuanSheZhi = anQuanSheZhi;
	}
	public String getShangWangKongZhi() {
		return shangWangKongZhi;
	}
	public void setShangWangKongZhi(String shangWangKongZhi) {
		this.shangWangKongZhi = shangWangKongZhi;
	}
	public String getXiTongGongJu() {
		return xiTongGongJu;
	}
	public void setXiTongGongJu(String xiTongGongJu) {
		this.xiTongGongJu = xiTongGongJu;
	}
	public String getWangLuoBiaoZhun() {
		return wangLuoBiaoZhun;
	}
	public void setWangLuoBiaoZhun(String wangLuoBiaoZhun) {
		this.wangLuoBiaoZhun = wangLuoBiaoZhun;
	}
	public String getTuoPuJieGou() {
		return tuoPuJieGou;
	}
	public void setTuoPuJieGou(String tuoPuJieGou) {
		this.tuoPuJieGou = tuoPuJieGou;
	}
	public String getWangLuoLeiXing() {
		return wangLuoLeiXing;
	}
	public void setWangLuoLeiXing(String wangLuoLeiXing) {
		this.wangLuoLeiXing = wangLuoLeiXing;
	}
	public String getJiaoHuanFangShi() {
		return jiaoHuanFangShi;
	}
	public void setJiaoHuanFangShi(String jiaoHuanFangShi) {
		this.jiaoHuanFangShi = jiaoHuanFangShi;
	}
	public String getZhuanFaSuLv() {
		return zhuanFaSuLv;
	}
	public void setZhuanFaSuLv(String zhuanFaSuLv) {
		this.zhuanFaSuLv = zhuanFaSuLv;
	}
	public String getDaiKuan() {
		return daiKuan;
	}
	public void setDaiKuan(String daiKuan) {
		this.daiKuan = daiKuan;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
