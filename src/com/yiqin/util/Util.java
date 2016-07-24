package com.yiqin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.SAUser;
import com.yiqin.pojo.User;
import com.yiqin.sa.interceptor.LoginSAInterceptor;
import com.yiqin.shop.bean.ProductView;

public class Util {

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}

	public static boolean isEmpty(@SuppressWarnings("rawtypes") Map map) {
		return map == null || map.size() == 0;
	}

	public static boolean isNotEmpty(@SuppressWarnings("rawtypes") Map map) {
		return map != null && map.size() > 0;
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

	public static void sortProductView(List<ProductView> list) {
		Collections.sort(list, new Comparator<ProductView>() {
            public int compare(ProductView arg0, ProductView arg1) {
                return arg0.getProductId().compareTo(arg1.getProductId());
            }
        });
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
	 * 将某个日期增加指定分钟，并返回结果。如果传入负数，则为减。
	 * 
	 * @param date
	 * @param ammount
	 * @return
	 */
	public static Date addMinute(final Date date, final int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, ammount);
		return c.getTime();
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
	 * 将某个日期增加指定年数，并返回结果。如果传入负数，则为减。
	 * 
	 * @param date
	 *            要操作的日期对象
	 * @param ammount
	 *            要增加年的数目
	 * @return 结果日期对象
	 */
	public static Date addYear(final Date date, final int ammount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, ammount);
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

	/**
	 * 将年的整数形式（yyyy）转换为日期对象返回。
	 * 
	 * @param year
	 *            年月的整数形式（yyyy）
	 * @return 日期类型
	 * @throws ParseException
	 */
	public static Date parseYear(final Integer year) {
		return parse(String.valueOf(year), "yyyy");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getDTOList(String jsonString, Class clazz) {
		JSONArray array = JSONArray.fromObject(jsonString);
		List list = new ArrayList();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			JSONObject jsonObject = (JSONObject) iter.next();
			list.add(JSONObject.toBean(jsonObject, clazz));
		}
		return list;
	}

	public static Object getBeanByJson(String json, Class clazz, Map classMap) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		if (classMap == null || classMap.size() == 0) {
			return JSONObject.toBean(jsonObject, clazz);
		} else {
			return JSONObject.toBean(jsonObject, clazz, classMap);
		}
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
	
	public static String faPiaoLx(String fplx){
		String fapiaolx = "普通发票";
		if ("1".equals(fplx)) {
			fapiaolx = "普通发票";
		} else if ("2".equals(fplx)) {
			fapiaolx = "专用发票";
		}
		return fapiaolx;
	}

	public static String orderStatusStr(Byte status) {
		String statusStr = "全部状态";
		if (status == 10) {
			statusStr = "全部状态";
		} else if (status == 1) {
			statusStr = "等待收货";
		} else if (status == 2) {
			statusStr = "等待付款";
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
		net.sf.json.JSONObject jsonString = net.sf.json.JSONObject.fromObject(obj);
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
	
	public static SAUser getSALoginUser(HttpSession session) {
		SAUser loninUser = null;
		Object userObj = session.getAttribute(LoginSAInterceptor.USER_SESSION_KEY);
		if (userObj != null) {
			loninUser = (SAUser) userObj;
		}
		return loninUser;
	}
	
	public static String verificationCodeProcess() {
		int length = 6;
		Date d = new Date();
		long lseed = d.getTime();
		// 设置随机种子
		java.util.Random r = new Random(lseed);
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < length; i++) {
			str.append(r.nextInt(9));// 生成随机数字
		}
		return str.toString();
	}
	
	public static String generateRegCode() {
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
				"u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z" };
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}
	
	public static List<Attribute> sort(List<Attribute> list) {
		Collections.sort(list, new Comparator<Attribute>() {
			@Override
			public int compare(Attribute o1, Attribute o2) {
				String nameId1 = o1.getNameId();
				String nameId2 = o2.getNameId();

				return nameId1.compareTo(nameId2);
			}
		});
		return list;
	}
	
	public static Map<Integer, String> sort(Map<Integer, String> attrid_pvalueMap) {
		if (Util.isEmpty(attrid_pvalueMap)) {
			return null;
		}
		Map<Integer, String> sortMap = new TreeMap<Integer, String>(
				new Comparator<Integer>() {
					@Override
					public int compare(Integer key1, Integer key2) {
						return key1.compareTo(key2);
					}
				});
		sortMap.putAll(attrid_pvalueMap);
		return sortMap;
	}

	/** 文件类型 */
	public enum FileType {
		/** pdf 文件 */
		PDF {
			public String getExtName() {
				return "pdf";
			}

			@Override
			public String toString() {
				return ".pdf";
			}
		},
		/** word 文件(.doc) */
		WORD {
			public String getExtName() {
				return "doc";
			}

			@Override
			public String toString() {
				return ".doc";
			}
		},
		/** html 文件 */
		HTML {
			public String getExtName() {
				return "html";
			}

			@Override
			public String toString() {
				return ".html";
			}
		},
		/** html 附件 */
		HTMLATT {
			public String getExtName() {
				return "html";
			}

			@Override
			public String toString() {
				return ".html";
			}
		},
		/** jpeg 文件 */
		JPEG {
			public String getExtName() {
				return "jpeg";
			}

			@Override
			public String toString() {
				return ".jpeg";
			}
		},
		/** gif 文件 */
		GIF {
			public String getExtName() {
				return "gif";
			}

			@Override
			public String toString() {
				return ".gif";
			}
		},
		/** jpg 文件 */
		JPG {
			public String getExtName() {
				return "jpg";
			}

			@Override
			public String toString() {
				return ".jpg";
			}
		},
		/** png 文件 */
		PNG {
			public String getExtName() {
				return "png";
			}

			@Override
			public String toString() {
				return ".png";
			}
		},
		/** xml 文件 */
		XML {
			public String getExtName() {
				return "xml";
			}

			@Override
			public String toString() {
				return ".xml";
			}
		};
		public String toString() {
			return null;
		}

		public String getExtName() {
			return null;
		}
	}
}
