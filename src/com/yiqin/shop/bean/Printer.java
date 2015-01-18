package com.yiqin.shop.bean;

public class Printer extends PrinterBase {

	private static final long serialVersionUID = -2051617213579934574L;
	
	// 打印机
	private String daYinSuDu;           // 打印速度
	private String shouYeShuChu;        // 首页输出
	private String daYinFenBianLv;      // 打印分辨率
	private String chuLiQi;             // 处理器
	private String neiCun;              // 内存
	private String yueYinLiang;         // 推荐月印量
	private String daYinFuHe;           // 打印负荷
	private String jinZhiHe;            // 进纸盒
	private String chuZhiHe;            // 出纸盒
	private String shuangMianDaYin;     // 双面打印
	private String zhiHeChiCun;         // 纸盒尺寸
	private String zhiHeZhongLiang;     // 纸盒重量
	private String jieZhiLeiXing;       // 打印介质类型
	private String jieKou;              // 接口
	private String yuyan;               // 语言和字体
	private String keHuDuanOS;          // 客户端操作系统
	private String wangLuoOS;           // 网络操作系统
	private String wangLuoXieYi;        // 网络协议
	private String gongZuoWenDu;        // 工作温度
	private String cunFangWenDu;        // 粗放温度
	private String gongZuoShiDu;        // 工作湿度
	private String cunFangShiDu;        // 存放湿度
	private String shengNeng;           // 声能
	private String shengYa;             // 声压
	private String dianYuan;            // 电源
	private String gongHao;             // 功耗
	private String dianLiang;           // 典型用电量
	private String anQuanRenZheng;      // 安全认证
	private String EMC;                 // EMC
	private String pinPaiZhiChi;        // 品牌支持

	// 3D 打印机参数
	private String xianShiPing;         // 显示屏
	private String lieYinJiShu;         // 列印技术
	private String chengXingChiCun;     // 最大成型尺寸
	private String lieYinMoShi;         // 列印模式
	private String lieYinPenZui;        // 烈焰喷嘴
	private String penZuiZhiJing;       // 喷嘴直径
	private String haoCaiZhiJing;       // 耗材直径
	private String haoCaiCaiZhi;        // 耗材材质
	private String zhiChengCaiZhi;      // 支撑材质
	private String dangAnGeShi;         // 档案格式
	private String xiTongDaPei;         // 系统搭配

	// 复印机
	private String fuYinFenBianLv;     // 复印分辨率
	private String fuYinSuDu;            // 复印速度
	private String fuYinYeShu;           // 复印页数

	public String getDaYinSuDu() {
		return daYinSuDu;
	}
	public void setDaYinSuDu(String daYinSuDu) {
		this.daYinSuDu = daYinSuDu;
	}
	public String getShouYeShuChu() {
		return shouYeShuChu;
	}
	public void setShouYeShuChu(String shouYeShuChu) {
		this.shouYeShuChu = shouYeShuChu;
	}
	public String getDaYinFenBianLv() {
		return daYinFenBianLv;
	}
	public void setDaYinFenBianLv(String daYinFenBianLv) {
		this.daYinFenBianLv = daYinFenBianLv;
	}
	public String getChuLiQi() {
		return chuLiQi;
	}
	public void setChuLiQi(String chuLiQi) {
		this.chuLiQi = chuLiQi;
	}
	public String getNeiCun() {
		return neiCun;
	}
	public void setNeiCun(String neiCun) {
		this.neiCun = neiCun;
	}
	public String getYueYinLiang() {
		return yueYinLiang;
	}
	public void setYueYinLiang(String yueYinLiang) {
		this.yueYinLiang = yueYinLiang;
	}
	public String getDaYinFuHe() {
		return daYinFuHe;
	}
	public void setDaYinFuHe(String daYinFuHe) {
		this.daYinFuHe = daYinFuHe;
	}
	public String getJinZhiHe() {
		return jinZhiHe;
	}
	public void setJinZhiHe(String jinZhiHe) {
		this.jinZhiHe = jinZhiHe;
	}
	public String getChuZhiHe() {
		return chuZhiHe;
	}
	public void setChuZhiHe(String chuZhiHe) {
		this.chuZhiHe = chuZhiHe;
	}
	public String getShuangMianDaYin() {
		return shuangMianDaYin;
	}
	public void setShuangMianDaYin(String shuangMianDaYin) {
		this.shuangMianDaYin = shuangMianDaYin;
	}
	public String getZhiHeChiCun() {
		return zhiHeChiCun;
	}
	public void setZhiHeChiCun(String zhiHeChiCun) {
		this.zhiHeChiCun = zhiHeChiCun;
	}
	public String getZhiHeZhongLiang() {
		return zhiHeZhongLiang;
	}
	public void setZhiHeZhongLiang(String zhiHeZhongLiang) {
		this.zhiHeZhongLiang = zhiHeZhongLiang;
	}
	public String getJieZhiLeiXing() {
		return jieZhiLeiXing;
	}
	public void setJieZhiLeiXing(String jieZhiLeiXing) {
		this.jieZhiLeiXing = jieZhiLeiXing;
	}
	public String getJieKou() {
		return jieKou;
	}
	public void setJieKou(String jieKou) {
		this.jieKou = jieKou;
	}
	public String getYuyan() {
		return yuyan;
	}
	public void setYuyan(String yuyan) {
		this.yuyan = yuyan;
	}
	public String getKeHuDuanOS() {
		return keHuDuanOS;
	}
	public void setKeHuDuanOS(String keHuDuanOS) {
		this.keHuDuanOS = keHuDuanOS;
	}
	public String getWangLuoOS() {
		return wangLuoOS;
	}
	public void setWangLuoOS(String wangLuoOS) {
		this.wangLuoOS = wangLuoOS;
	}
	public String getWangLuoXieYi() {
		return wangLuoXieYi;
	}
	public void setWangLuoXieYi(String wangLuoXieYi) {
		this.wangLuoXieYi = wangLuoXieYi;
	}
	public String getGongZuoWenDu() {
		return gongZuoWenDu;
	}
	public void setGongZuoWenDu(String gongZuoWenDu) {
		this.gongZuoWenDu = gongZuoWenDu;
	}
	public String getCunFangWenDu() {
		return cunFangWenDu;
	}
	public void setCunFangWenDu(String cunFangWenDu) {
		this.cunFangWenDu = cunFangWenDu;
	}
	public String getGongZuoShiDu() {
		return gongZuoShiDu;
	}
	public void setGongZuoShiDu(String gongZuoShiDu) {
		this.gongZuoShiDu = gongZuoShiDu;
	}
	public String getCunFangShiDu() {
		return cunFangShiDu;
	}
	public void setCunFangShiDu(String cunFangShiDu) {
		this.cunFangShiDu = cunFangShiDu;
	}
	public String getShengNeng() {
		return shengNeng;
	}
	public void setShengNeng(String shengNeng) {
		this.shengNeng = shengNeng;
	}
	public String getShengYa() {
		return shengYa;
	}
	public void setShengYa(String shengYa) {
		this.shengYa = shengYa;
	}
	public String getDianYuan() {
		return dianYuan;
	}
	public void setDianYuan(String dianYuan) {
		this.dianYuan = dianYuan;
	}
	public String getGongHao() {
		return gongHao;
	}
	public void setGongHao(String gongHao) {
		this.gongHao = gongHao;
	}
	public String getDianLiang() {
		return dianLiang;
	}
	public void setDianLiang(String dianLiang) {
		this.dianLiang = dianLiang;
	}
	public String getAnQuanRenZheng() {
		return anQuanRenZheng;
	}
	public void setAnQuanRenZheng(String anQuanRenZheng) {
		this.anQuanRenZheng = anQuanRenZheng;
	}
	public String getEMC() {
		return EMC;
	}
	public void setEMC(String eMC) {
		EMC = eMC;
	}
	public String getPinPaiZhiChi() {
		return pinPaiZhiChi;
	}
	public void setPinPaiZhiChi(String pinPaiZhiChi) {
		this.pinPaiZhiChi = pinPaiZhiChi;
	}
	public String getXianShiPing() {
		return xianShiPing;
	}
	public void setXianShiPing(String xianShiPing) {
		this.xianShiPing = xianShiPing;
	}
	public String getLieYinJiShu() {
		return lieYinJiShu;
	}
	public void setLieYinJiShu(String lieYinJiShu) {
		this.lieYinJiShu = lieYinJiShu;
	}
	public String getChengXingChiCun() {
		return chengXingChiCun;
	}
	public void setChengXingChiCun(String chengXingChiCun) {
		this.chengXingChiCun = chengXingChiCun;
	}
	public String getLieYinMoShi() {
		return lieYinMoShi;
	}
	public void setLieYinMoShi(String lieYinMoShi) {
		this.lieYinMoShi = lieYinMoShi;
	}
	public String getLieYinPenZui() {
		return lieYinPenZui;
	}
	public void setLieYinPenZui(String lieYinPenZui) {
		this.lieYinPenZui = lieYinPenZui;
	}
	public String getPenZuiZhiJing() {
		return penZuiZhiJing;
	}
	public void setPenZuiZhiJing(String penZuiZhiJing) {
		this.penZuiZhiJing = penZuiZhiJing;
	}
	public String getHaoCaiZhiJing() {
		return haoCaiZhiJing;
	}
	public void setHaoCaiZhiJing(String haoCaiZhiJing) {
		this.haoCaiZhiJing = haoCaiZhiJing;
	}
	public String getHaoCaiCaiZhi() {
		return haoCaiCaiZhi;
	}
	public void setHaoCaiCaiZhi(String haoCaiCaiZhi) {
		this.haoCaiCaiZhi = haoCaiCaiZhi;
	}
	public String getZhiChengCaiZhi() {
		return zhiChengCaiZhi;
	}
	public void setZhiChengCaiZhi(String zhiChengCaiZhi) {
		this.zhiChengCaiZhi = zhiChengCaiZhi;
	}
	public String getDangAnGeShi() {
		return dangAnGeShi;
	}
	public void setDangAnGeShi(String dangAnGeShi) {
		this.dangAnGeShi = dangAnGeShi;
	}
	public String getXiTongDaPei() {
		return xiTongDaPei;
	}
	public void setXiTongDaPei(String xiTongDaPei) {
		this.xiTongDaPei = xiTongDaPei;
	}
	
}
