package com.yiqin.shop.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yiqin.shop.bean.ProductView;
import com.yiqin.shop.dao.IProductDao;
import com.yiqin.shop.pojo.Category;
import com.yiqin.shop.pojo.Product;
import com.yiqin.shop.service.ProductManager;
import com.yiqin.util.Util;

public class ProductManagerImpl implements ProductManager {

	private IProductDao productDao;

	public IProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public List<Product> findProductInfoById(String pids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductView> findProductInfo(String categorys) {
		if (Util.isEmpty(categorys)) {
			return null;
		}
		if (categorys.length() >= 3) {
			categorys = categorys.substring(0, 1) + categorys;
		}
		List<Product> productList = productDao.findProductInfoByCategorys(categorys);
		if (Util.isEmpty(productList)) {
			return null;
		}
		List<ProductView> pViewList = new ArrayList<ProductView>();
		Set<String> pidSet = new HashSet<String>();
		for (Product product : productList) {
			pidSet.add(product.getProductId());
		}
		for (String pid : pidSet) {
			ProductView productView = new ProductView();
			productView.setProductId(pid);
			for (Product product : productList) {
				if (pid.equals(product.getProductId())) {
					if (product.getAttributeId() == 1) {
						productView.setProductName(product.getValue());
					}
					if (product.getAttributeId() == 5) {
						productView.setImgUrl(product.getValue());
					}
					if (product.getAttributeId() == 2) {
						productView.setPrice(product.getValue());
					}
				}
			}
			pViewList.add(productView);
		}
		return pViewList;
	}

	@Override
	public List<Category> findCategoryInfo() {
		List<Category> categoryList = productDao.findCategoryInfo();

		List<Category> tempCategory = new ArrayList<Category>();
		if (Util.isNotEmpty(categoryList)) {
			for (Category cate : categoryList) {
				int parentId = cate.getParentId();
				if (parentId == 0) {
					tempCategory.add(cate);
				}
			}
			handleSubCategory(categoryList, tempCategory);
		}
		return tempCategory;
	}

	private void handleSubCategory(List<Category> allCategoryList,
			List<Category> superCategory) {
		for (Category superCate : superCategory) {
			int id = superCate.getId();
			List<Category> subCategory = new ArrayList<Category>();
			for (Category cate : allCategoryList) {
				if (id == cate.getParentId()) {
					subCategory.add(cate);
				}
			}
			superCate.setSubCategoryList(subCategory);
			List<Category> subCateList = superCate.getSubCategoryList();
			if (Util.isNotEmpty(subCateList)) {
				handleSubCategory(allCategoryList, subCateList);
			}
		}
	}
}
