package com.yiqin.bean.bk;

import com.yiqin.util.Util;

public class KaoQinJi extends ProductBase {

	private static final long serialVersionUID = -8164149657495019799L;
	private String renZhengFangShi;          // 认证方式
	private String chuLiQi;                  // 处理器
	private String xianShi    ;              // 显示
	private String yuYin;                    // 语音
	private String caoZuoXiTong;             // 操作系统
	private String cunChuJieZhi;             // 存储介质
	private String daKaSuDu;                 // 打卡速度
	private String tingDianDaKa;             // 停电打卡
	public String getRenZhengFangShi() {
		return renZhengFangShi;
	}
	public void setRenZhengFangShi(String renZhengFangShi) {
		this.renZhengFangShi = renZhengFangShi;
	}
	public String getChuLiQi() {
		return chuLiQi;
	}
	public void setChuLiQi(String chuLiQi) {
		this.chuLiQi = chuLiQi;
	}
	public String getXianShi() {
		return xianShi;
	}
	public void setXianShi(String xianShi) {
		this.xianShi = xianShi;
	}
	public String getYuYin() {
		return yuYin;
	}
	public void setYuYin(String yuYin) {
		this.yuYin = yuYin;
	}
	public String getCaoZuoXiTong() {
		return caoZuoXiTong;
	}
	public void setCaoZuoXiTong(String caoZuoXiTong) {
		this.caoZuoXiTong = caoZuoXiTong;
	}
	public String getCunChuJieZhi() {
		return cunChuJieZhi;
	}
	public void setCunChuJieZhi(String cunChuJieZhi) {
		this.cunChuJieZhi = cunChuJieZhi;
	}
	public String getDaKaSuDu() {
		return daKaSuDu;
	}
	public void setDaKaSuDu(String daKaSuDu) {
		this.daKaSuDu = daKaSuDu;
	}
	public String getTingDianDaKa() {
		return tingDianDaKa;
	}
	public void setTingDianDaKa(String tingDianDaKa) {
		this.tingDianDaKa = tingDianDaKa;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
