package com.yiqin.bean.bk;

import com.yiqin.util.Util;

public class MoHe extends ProductBase {

	private static final long serialVersionUID = -1876507781469634402L;
	private String shiYongJiXing;      // 适用机型

	public String getShiYongJiXing() {
		return shiYongJiXing;
	}
	public void setShiYongJiXing(String shiYongJiXing) {
		this.shiYongJiXing = shiYongJiXing;
	}

	public String toString() {
		return Util.objToString(this);
	}
}
