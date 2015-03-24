package com.yiqin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yiqin.shop.pojo.User;

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

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取当天零点时间
	 * 
	 * @return Date
	 */
	public static Date getCurrentStartTime() {
		long time = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 将某个日期增加指定天数，并返回结果。如果传入负数，则为减。
	 * 
	 * @param date
	 *            要操作的日期对象
	 * @param ammount
	 *            要增加天的数目
	 * @return 结果日期对象
	 */
	public static Date addDay(final Date date, final int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, ammount);
		return c.getTime();
	}

	/**
	 * 将某个日期增加指定月数，并返回结果。如果传入负数，则为减。
	 * 
	 * @param date
	 *            要操作的日期对象
	 * @param ammount
	 *            要增加月的数目
	 * @return 结果日期对象
	 */
	public static Date addMonth(final Date date, final int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, ammount);
		return c.getTime();
	}

	/**
	 * 将传入的字符串按照默认格式转换为日期对象（yyyy-MM-dd）
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @return 转换后的日期对象
	 * @throws ParseException
	 *             如果传入的字符串格式不符合默认格式（如果传入的字符串格式不合法）
	 */
	public static Date parse(final String dateStr) throws ParseException {
		return parse(dateStr, "yyyy-MM-dd");
	}

	/**
	 * 根据传入的日期格式化patter将传入的字符串转换成日期对象
	 * 
	 * @param dateStr
	 *            要转换的字符串
	 * @param pattern
	 *            日期格式化pattern
	 * @return 转换后的日期对象
	 * @throws ParseException
	 *             如果传入的字符串格式不合法
	 */
	public static Date parse(final String dateStr, final String pattern) {
		Date date = null;
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据传入的日期格式化pattern将传入的日期格式化成字符串。
	 * 
	 * @param date
	 *            要格式化的日期对象
	 * @param pattern
	 *            日期格式化pattern
	 * @return 格式化后的日期字符串
	 */
	public static String format(final Date date, final String pattern) {
		if (date == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 将传入的日期按照默认形势转换成字符串（yyyy-MM-dd）
	 * 
	 * @param date
	 *            要格式化的日期对象
	 * @return 格式化后的日期字符串
	 */
	public static String format(final Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static List getDTOList(String jsonString, Class clazz) {
		JSONArray array = JSONArray.fromObject(jsonString);
		List list = new ArrayList();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			JSONObject jsonObject = (JSONObject) iter.next();
			list.add(JSONObject.toBean(jsonObject, clazz));
		}
		return list;
	}

	public static String peiSongFangShi(String peisong) {
		String peisongfangshi = "依勤送货";
		if ("1".equals(peisong)) {
			peisongfangshi = "依勤送货";
		} else if ("2".equals(peisong)) {
			peisongfangshi = "上门自提";
		}
		return peisongfangshi;
	}

	public static String orderStatusStr(Byte status) {
		String statusStr = "全部状态";
		if (status == 10) {
			statusStr = "全部状态";
		} else if (status == 1) {
			statusStr = "等待付款";
		} else if (status == 2) {
			statusStr = "等待收货";
		} else if (status == 3) {
			statusStr = "已完成";
		} else if (status == 0) {
			statusStr = "已取消";
		}
		return statusStr;
	}

	public static String objToString(Object obj) {
		if (obj == null)
			return null;
		net.sf.json.JSONObject jsonString = net.sf.json.JSONObject
				.fromObject(obj);
		String content = jsonString.toString();
		return content;
	}

	public static User getLoginUser(HttpSession session) {
		User loninUser = null;
		Object userObj = session.getAttribute("userInfo");
		if (userObj != null) {
			loninUser = (User) userObj;
		}
		return loninUser;
	}
}
