package com.yiqin.shop.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.yiqin.shop.bean.Category;
import com.yiqin.shop.bean.CategorySub;
import com.yiqin.shop.bean.ProductBase;
import com.yiqin.shop.service.ProductManager;

public class ProductManagerImpl implements ProductManager {

	// private ProductDao productDao;
	//
	// public ProductDao getProductDao() {
	// return productDao;
	// }
	//
	// public void setProductDao(ProductDao productDao) {
	// this.productDao = productDao;
	// }

	@Override
	public List<ProductBase> findProductInfoById(String pids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductBase> findProductInfo(String categorys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findCategoryInfo() {
		List<Category> cate = new ArrayList<>();
		
		Category category = new Category();
		category.setId("100000");
		category.setLevel(1);
		category.setName("打印耗材");
		
		List<CategorySub> subCategory = new ArrayList();
		CategorySub caSub = new CategorySub();
		caSub.setId("100001");
		caSub.setLevel(1);
		caSub.setName("打印纸");
		subCategory.add(caSub);
		
		CategorySub caSub1= new CategorySub();
		caSub1.setId("100002");
		caSub1.setLevel(2);
		caSub1.setName("打印机");
		subCategory.add(caSub1);
		category.setSubCategory(subCategory);
		
		Category category1 = new Category();
		category1.setId("200000");
		category1.setLevel(1);
		category1.setName("办公用品");
		
		List<CategorySub> subCategory1 = new ArrayList();
		CategorySub caSub2 = new CategorySub();
		caSub2.setId("200001");
		caSub2.setLevel(1);
		caSub2.setName("办公纸");
		subCategory1.add(caSub2);
		
		CategorySub caSub3= new CategorySub();
		caSub3.setId("200002");
		caSub3.setLevel(2);
		caSub3.setName("钢笔");
		subCategory1.add(caSub3);
		category1.setSubCategory(subCategory1);
		
		
		Category category2 = new Category();
		category2.setId("300000");
		category2.setLevel(1);
		category2.setName("办公电脑");
		
		cate.add(category);
		cate.add(category1);
		cate.add(category2);
		
		return cate;
	}
}