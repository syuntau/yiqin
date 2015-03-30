package com.yiqin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yiqin.pojo.Category;
import com.yiqin.service.ProductManager;
import com.yiqin.serviceImpl.ProductManagerImpl;

public class CategoryUtil {

	private static CategoryUtil categoryUtil = new CategoryUtil();
	private static List<Category> categoryList = new ArrayList<Category>();
	private static List<CategorySimple> firstCategorySimpleList = new ArrayList<CategorySimple>();;
	private static Map<String, List<Category>> categoryMap = new HashMap<String, List<Category>>();
	private static Map<String, List<CategorySimple>> categorySimpleMap = new HashMap<String, List<CategorySimple>>();

	private CategoryUtil() {
		ProductManager productManager = new ProductManagerImpl();
		categoryList = productManager.findCategoryInfo();
		for (Category category : categoryList) {
			firstCategorySimpleList.add(new CategorySimple(category));
		}
		setCategoryMap(categoryList);
		setCategorySimpleMap();
	}

	public CategoryUtil getInstance() {
		return categoryUtil;
	}

	public static List<CategorySimple> getFirstCategory() {
		return firstCategorySimpleList;
	}

	public static List<CategorySimple> getCategoryListById(String categoryId) {
		return categorySimpleMap.get(categoryId);
	}

	private static void setCategoryMap(List<Category> list) {
		if (Util.isEmpty(list)) {
			return ;
		}
		for (Category category : list) {
			List<Category> subList = category.getSubCategoryList();
			if (Util.isNotEmpty(subList)) {
				categoryMap.put(String.valueOf(category.getId()), subList);
				setCategoryMap(subList);
			}
		}
	}

	private static void setCategorySimpleMap() {
		for (String key : categoryMap.keySet()) {
			List<CategorySimple> tempList = new ArrayList<CategorySimple>();
			for (Category category : categoryMap.get(key)) {
				tempList.add(new CategorySimple(category));
			}
			categorySimpleMap.put(key, tempList);
		}
	}
}
