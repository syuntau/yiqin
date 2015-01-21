package com.yiqin.util;

import java.util.List;

public class Util {

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}

	public static boolean isEmpty(@SuppressWarnings("rawtypes") List list) {
		return list == null || list.size() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}

	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") List list) {
		return list != null && list.size() > 0;
	}

	public static String objToString(Object obj) {
		if(obj==null) return null;
		net.sf.json.JSONObject jsonString = net.sf.json.JSONObject.fromObject(obj);
		String content=jsonString.toString();
		return content;
	}
}
