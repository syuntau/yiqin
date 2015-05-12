package com.yiqin.bean.bk;

import com.yiqin.util.Util;

public class FuYinZhi extends ProductBase {

	private static final long serialVersionUID = -5516142417214040291L;
	private String zhiZhangGuiGe;              // 纸张规格
	private String baoZhuangShuLiang;          // 包装数量

	public String getZhiZhangGuiGe() {
		return zhiZhangGuiGe;
	}
	public void setZhiZhangGuiGe(String zhiZhangGuiGe) {
		this.zhiZhangGuiGe = zhiZhangGuiGe;
	}
	public String getBaoZhuangShuLiang() {
		return baoZhuangShuLiang;
	}
	public void setBaoZhuangShuLiang(String baoZhuangShuLiang) {
		this.baoZhuangShuLiang = baoZhuangShuLiang;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
