package com.yiqin.bean.bk;

import com.yiqin.util.Util;

public class XiGu extends ProductBase {

	private static final long serialVersionUID = 5620506060192857990L;
	private String fengKouLeiXing;        // 封口类型
	private String caiZhi;                // 材质
	private String shiYongJiXing;         // 适用机型
	private String tanFenLiang;           // 碳粉量
	private String daYinYeShu;            // 打印页数
	public String getFengKouLeiXing() {
		return fengKouLeiXing;
	}
	public void setFengKouLeiXing(String fengKouLeiXing) {
		this.fengKouLeiXing = fengKouLeiXing;
	}
	public String getCaiZhi() {
		return caiZhi;
	}
	public void setCaiZhi(String caiZhi) {
		this.caiZhi = caiZhi;
	}
	public String getShiYongJiXing() {
		return shiYongJiXing;
	}
	public void setShiYongJiXing(String shiYongJiXing) {
		this.shiYongJiXing = shiYongJiXing;
	}
	public String getTanFenLiang() {
		return tanFenLiang;
	}
	public void setTanFenLiang(String tanFenLiang) {
		this.tanFenLiang = tanFenLiang;
	}
	public String getDaYinYeShu() {
		return daYinYeShu;
	}
	public void setDaYinYeShu(String daYinYeShu) {
		this.daYinYeShu = daYinYeShu;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
