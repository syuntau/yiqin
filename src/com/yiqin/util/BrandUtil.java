package com.yiqin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yiqin.dao.IProductDao;
import com.yiqin.pojo.Brand;

/**
 * 所有品牌
 * 
 * @author LiuJun
 * 
 */
public class BrandUtil {

	private static boolean isInit = false;
	private static List<Brand> brandList = new ArrayList<Brand>();
	private static Map<Integer, Brand> brandMap = new HashMap<Integer, Brand>();

	public static Brand getBrand(Integer brandId) {
		return brandMap.get(brandId);
	}
	
	public static List<Brand> getBrandList() {
		return brandList;
	}
	
	public static Map<Integer, Brand> getBrandMap() {
		return brandMap;
	}

	public static void init(IProductDao productDao) {
		if (isInit) return ;
		brandList = productDao.findAllBrand();
		for (Brand brand : brandList) {
			brandMap.put(brand.getId(), brand);
		}
		isInit = true;
	}
}
