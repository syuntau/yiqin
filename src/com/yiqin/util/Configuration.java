package com.yiqin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Configuration {

	private static Configuration config = new Configuration();
	private static Properties appProperties;
	private static String appResources;
	private static Map<String, List<String>> propertyListMap;
	private static Map<String, PropertyEntry> propertyEntryMap;

	private Configuration() {
		appProperties = new Properties();
		appResources = "/config_shop.properties";
		propertyListMap = new HashMap<String, List<String>>();
		propertyEntryMap = new HashMap<String, PropertyEntry>();
		load();
		initPropertyListMap();
		initPropertyEntryMap();
	}

	/**
	 * 载入资源文件
	 */
	private void load() {
		try {
			InputStream appStream = Configuration.class
					.getResourceAsStream(appResources);
			appProperties.load(appStream);
			appStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据字段<b> [ init.properties.item ] </b>记录的key，初始化属性列表Map
	 */
	private void initPropertyListMap() {
		String properties = getProperty("init.properties.item");
		if (Util.isEmpty(properties))
			return;
		String[] keyArr = properties.split(",");
		for (String key : keyArr) {
			String values = getProperty(key);
			if (Util.isNotEmpty(values)) {
				propertyListMap.put(key, Arrays.asList(values.split(",")));
			}
		}
	}

	/**
	 * 根据字段<b> [ init.properties.entry ] </b>记录的key，初始化属性键值Map
	 */
	private void initPropertyEntryMap() {
		String properties = getProperty("init.properties.entry");
		if (Util.isEmpty(properties))
			return;
		String[] propertyArr = properties.split(",");

		for (String property : propertyArr) {
			String values = getProperty(property);
			if (Util.isNotEmpty(values)) {
				PropertyEntry propertyEntry = new PropertyEntry();
				String[] entryArr = values.split(",");
				for (String entry : entryArr) {
					if (Util.isNotEmpty(entry)) {
						String[] temp = entry.split(":");
						if (temp.length == 2) {
							propertyEntry.getKeyList().add(temp[0]);
							propertyEntry.getValueList().add(temp[1]);
							propertyEntry.getPropertyMap()
									.put(temp[0], temp[1]);
						}
					}
				}
				propertyEntryMap.put(property, propertyEntry);
			}
		}
	}

	/**
	 * 取得Configuration实例
	 * 
	 * @return Configuration实例
	 */
	public static Configuration getInstance() {
		return config;
	}

	/**
	 * 根据指定key取得值。
	 * 
	 * @param key
	 * @return key对应值
	 */
	public static String getProperty(String key) {
		String value = null;
		if (key != null && !"".equals(key)) {
			value = appProperties.getProperty(key);
		}
		// logger.info(Tools.makeLogInfo(SystemTypeEnum.PLATFORM, null, null,
		// null, null, new
		// StringBuilder().append("the value of key [").append(key).append("] is : ").append(value).toString()));
		return value;
	}

	/**
	 * 根据指定key取得key列表。但指定key必须在资源文件的字段<b> [ init.properties.entry ] </b>中注册。
	 * <p>
	 * 例：init.properties.entry=tab.list<br>
	 * tab.list=all:0,newspaper:1,web:2,blog:3,bbs:4<br>
	 * getKeyList("tab.list") -> [all, newspaper, web, blog, bbs]
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> getKeyList(String key) {
		if (Util.isEmpty(propertyEntryMap))
			return null;
		PropertyEntry propertyEntry = propertyEntryMap.get(key);
		if (propertyEntry == null)
			return null;
		return propertyEntry.getKeyList();
	}

	/**
	 * 根据指定key取得value列表。但指定key必须在资源文件的字段<b> [ init.properties.entry ] </b>中注册。
	 * <p>
	 * 例：init.properties.entry=tab.list<br>
	 * tab.list=all:0,newspaper:1,web:2,blog:3,bbs:4<br>
	 * getValueList("tab.list") -> [0, 1, 2, 3, 4]
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> getValueList(String key) {
		if (Util.isEmpty(key) || Util.isEmpty(propertyEntryMap))
			return null;
		PropertyEntry propertyEntry = propertyEntryMap.get(key);
		if (propertyEntry == null)
			return null;
		return propertyEntry.getValueList();
	}

	/**
	 * 根据指定key取得键值Map。但指定key必须在资源文件的字段<b> [ init.properties.entry ] </b>中注册。
	 * <p>
	 * 例：init.properties.entry=tab.list<br>
	 * tab.list=all:0,newspaper:1,web:2,blog:3,bbs:4<br>
	 * getPropertyMap("tab.list") -> [&lt;all:0&gt;, &lt;newspaper:1&gt;,
	 * &lt;web:2&gt;, &lt;blog:3&gt;, &lt;bbs:4&gt;]
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, String> getPropertyMap(String key) {
		if (Util.isEmpty(propertyEntryMap))
			return null;
		PropertyEntry propertyEntry = propertyEntryMap.get(key);
		if (propertyEntry == null)
			return null;
		return propertyEntry.getPropertyMap();
	}

	/**
	 * 根据指定key取得其内容List。但指定key必须在资源文件的字段<b> [ init.properties.item ] </b>中注册。
	 * <p>
	 * 例：init.properties.item=poas.feature.list<br>
	 * poas.feature.list=print,download,mail<br>
	 * getPropertyList("poas.feature.list") -> [print,download,mail]
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> getPropertyList(String key) {
		if (Util.isEmpty(key) || Util.isEmpty(propertyListMap))
			return null;
		return propertyListMap.get(key);
	}

	/**
	 * 存储属性键值类。
	 * <p>
	 * 例：tab.list=all:0,newspaper:1,web:2,blog:3,bbs:4<br>
	 * getKeyList -> [all, newspaper, web, blog, bbs]<br>
	 * getValueList -> [0, 1, 2, 3, 4]<br>
	 * getPropertyMap -> [&lt;all:0&gt;, &lt;newspaper:1&gt;, &lt;web:2&gt;,
	 * &lt;blog:3&gt;, &lt;bbs:4&gt;]
	 * 
	 * @author tao
	 *
	 */
	class PropertyEntry {
		private List<String> keyList = new ArrayList<String>();
		private List<String> valueList = new ArrayList<String>();
		private Map<String, String> propertyMap = new HashMap<String, String>();

		/**
		 * @return the keyList
		 */
		public List<String> getKeyList() {
			return keyList;
		}

		/**
		 * @return the valueList
		 */
		public List<String> getValueList() {
			return valueList;
		}

		/**
		 * @return the propertyMap
		 */
		public Map<String, String> getPropertyMap() {
			return propertyMap;
		}
	}
}
