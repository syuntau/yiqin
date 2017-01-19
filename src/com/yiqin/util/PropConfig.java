package com.yiqin.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PropConfig {

	private volatile static Map<String, ResourceBundle> map = new HashMap<String, ResourceBundle>();

	public static ResourceBundle getBundle(String fileName) {
		ResourceBundle bundle = map.get(fileName);
		if (bundle == null) {
			synchronized (PropConfig.class) {
				bundle = map.get(fileName);
				if (bundle == null) {
					bundle = ResourceBundle.getBundle(fileName);
					map.put(fileName, bundle);
				}
			}
		}
		return bundle;
	}

	public static String getFileConfig(String fileName, String key) {
		return getBundle(fileName).getString(key);
	}

}
