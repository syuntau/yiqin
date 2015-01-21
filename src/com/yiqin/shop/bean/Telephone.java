package com.yiqin.shop.bean;

import com.yiqin.util.Util;

public class Telephone extends ProductBase {

	private static final long serialVersionUID = -8715405684143809313L;
	private String pingMu;                  // 屏幕显示
	private String dianHuaBu;               // 电话簿

	// 电话
	private String lingSheng;               // 铃声
	private String laiDianBaoHao;           // 来电报号
	private String huiBo;                   // 回拨功能
	private String shiPeiDianChi;           // 适配电池
	private String jingYin;                 // 静音功能
	private String laiDianXianShi;          // 来电显示
	private String shiJianXianShi;          // 时间显示
	private String yeGuangZhaoMing;         // 夜光照明
	private String mianTi;                  // 免提功能
	private String haoMaFanCha;             // 号码翻查
	private String luYin;                   // 录音功能
	private String tingDianShiYong;         // 停电使用
	private String chongBo;                 // 重播功能
	private String waiJieFenJi;             // 外接分机
	private String yiJianBoHao;             // 一键拨号
	private String yinLiangTiaoJie;         // 音量调节
	private String tongHuaShiChang;         // 通话时长
	private String ziJiKuoZhan;             // 子机扩展

	// 传真
	private String saoMiaoKuanDu;           // 有效扫描宽度
	private String fenBianLv;               // 分辨率
	private String chuanSongSuDu;           // 传送速度
	private String huiDuDengJi;             // 灰度等级
	private String ziDongSongGao;           // 自动送稿
	private String neiCun;                  // 内存
	private String wuZhiJieShou;            // 无纸接收
	private String lunXunLeiXing;           // 轮询类型
	private String suoFangFanWei;           // 缩放范围
	private String haoDianLiang;            // 耗电量
	private String zhiHeRongLiang;          // 纸盒容量
	private String haoCai;                  // 耗材

	public String getPingMu() {
		return pingMu;
	}
	public void setPingMu(String pingMu) {
		this.pingMu = pingMu;
	}
	public String getLingSheng() {
		return lingSheng;
	}
	public void setLingSheng(String lingSheng) {
		this.lingSheng = lingSheng;
	}
	public String getLaiDianBaoHao() {
		return laiDianBaoHao;
	}
	public void setLaiDianBaoHao(String laiDianBaoHao) {
		this.laiDianBaoHao = laiDianBaoHao;
	}
	public String getHuiBo() {
		return huiBo;
	}
	public void setHuiBo(String huiBo) {
		this.huiBo = huiBo;
	}
	public String getShiPeiDianChi() {
		return shiPeiDianChi;
	}
	public void setShiPeiDianChi(String shiPeiDianChi) {
		this.shiPeiDianChi = shiPeiDianChi;
	}
	public String getJingYin() {
		return jingYin;
	}
	public void setJingYin(String jingYin) {
		this.jingYin = jingYin;
	}
	public String getLaiDianXianShi() {
		return laiDianXianShi;
	}
	public void setLaiDianXianShi(String laiDianXianShi) {
		this.laiDianXianShi = laiDianXianShi;
	}
	public String getShiJianXianShi() {
		return shiJianXianShi;
	}
	public void setShiJianXianShi(String shiJianXianShi) {
		this.shiJianXianShi = shiJianXianShi;
	}
	public String getYeGuangZhaoMing() {
		return yeGuangZhaoMing;
	}
	public void setYeGuangZhaoMing(String yeGuangZhaoMing) {
		this.yeGuangZhaoMing = yeGuangZhaoMing;
	}
	public String getMianTi() {
		return mianTi;
	}
	public void setMianTi(String mianTi) {
		this.mianTi = mianTi;
	}
	public String getHaoMaFanCha() {
		return haoMaFanCha;
	}
	public void setHaoMaFanCha(String haoMaFanCha) {
		this.haoMaFanCha = haoMaFanCha;
	}
	public String getLuYin() {
		return luYin;
	}
	public void setLuYin(String luYin) {
		this.luYin = luYin;
	}
	public String getTingDianShiYong() {
		return tingDianShiYong;
	}
	public void setTingDianShiYong(String tingDianShiYong) {
		this.tingDianShiYong = tingDianShiYong;
	}
	public String getChongBo() {
		return chongBo;
	}
	public void setChongBo(String chongBo) {
		this.chongBo = chongBo;
	}
	public String getWaiJieFenJi() {
		return waiJieFenJi;
	}
	public void setWaiJieFenJi(String waiJieFenJi) {
		this.waiJieFenJi = waiJieFenJi;
	}
	public String getYiJianBoHao() {
		return yiJianBoHao;
	}
	public void setYiJianBoHao(String yiJianBoHao) {
		this.yiJianBoHao = yiJianBoHao;
	}
	public String getDianHuaBu() {
		return dianHuaBu;
	}
	public void setDianHuaBu(String dianHuaBu) {
		this.dianHuaBu = dianHuaBu;
	}
	public String getYinLiangTiaoJie() {
		return yinLiangTiaoJie;
	}
	public void setYinLiangTiaoJie(String yinLiangTiaoJie) {
		this.yinLiangTiaoJie = yinLiangTiaoJie;
	}
	public String getTongHuaShiChang() {
		return tongHuaShiChang;
	}
	public void setTongHuaShiChang(String tongHuaShiChang) {
		this.tongHuaShiChang = tongHuaShiChang;
	}
	public String getZiJiKuoZhan() {
		return ziJiKuoZhan;
	}
	public void setZiJiKuoZhan(String ziJiKuoZhan) {
		this.ziJiKuoZhan = ziJiKuoZhan;
	}
	public String getSaoMiaoKuanDu() {
		return saoMiaoKuanDu;
	}
	public void setSaoMiaoKuanDu(String saoMiaoKuanDu) {
		this.saoMiaoKuanDu = saoMiaoKuanDu;
	}
	public String getFenBianLv() {
		return fenBianLv;
	}
	public void setFenBianLv(String fenBianLv) {
		this.fenBianLv = fenBianLv;
	}
	public String getChuanSongSuDu() {
		return chuanSongSuDu;
	}
	public void setChuanSongSuDu(String chuanSongSuDu) {
		this.chuanSongSuDu = chuanSongSuDu;
	}
	public String getHuiDuDengJi() {
		return huiDuDengJi;
	}
	public void setHuiDuDengJi(String huiDuDengJi) {
		this.huiDuDengJi = huiDuDengJi;
	}
	public String getZiDongSongGao() {
		return ziDongSongGao;
	}
	public void setZiDongSongGao(String ziDongSongGao) {
		this.ziDongSongGao = ziDongSongGao;
	}
	public String getNeiCun() {
		return neiCun;
	}
	public void setNeiCun(String neiCun) {
		this.neiCun = neiCun;
	}
	public String getWuZhiJieShou() {
		return wuZhiJieShou;
	}
	public void setWuZhiJieShou(String wuZhiJieShou) {
		this.wuZhiJieShou = wuZhiJieShou;
	}
	public String getLunXunLeiXing() {
		return lunXunLeiXing;
	}
	public void setLunXunLeiXing(String lunXunLeiXing) {
		this.lunXunLeiXing = lunXunLeiXing;
	}
	public String getSuoFangFanWei() {
		return suoFangFanWei;
	}
	public void setSuoFangFanWei(String suoFangFanWei) {
		this.suoFangFanWei = suoFangFanWei;
	}
	public String getHaoDianLiang() {
		return haoDianLiang;
	}
	public void setHaoDianLiang(String haoDianLiang) {
		this.haoDianLiang = haoDianLiang;
	}
	public String getZhiHeRongLiang() {
		return zhiHeRongLiang;
	}
	public void setZhiHeRongLiang(String zhiHeRongLiang) {
		this.zhiHeRongLiang = zhiHeRongLiang;
	}
	public String getHaoCai() {
		return haoCai;
	}
	public void setHaoCai(String haoCai) {
		this.haoCai = haoCai;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
