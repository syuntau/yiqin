package com.yiqin.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.yiqin.dao.IProductDao;
import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.Product;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductFilter;
import com.yiqin.shop.bean.ProductView;
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
	public List<ProductView> findProductInfoById(String pids) {
		if (Util.isEmpty(pids)) {
			return null;
		}
		if (pids.contains(",")) {
			if (pids.startsWith(",")) {
				pids = pids.substring(1);
			}
			if (pids.endsWith(",")) {
				pids = pids.substring(0, pids.length() - 1);
			}
		}
		List<Product> productList = productDao.findProductInfoById(pids);
		if(Util.isEmpty(productList)){
			return null;
		}
		List<ProductView> pViewList = new ArrayList<ProductView>();
		Set<String> pidSet = new HashSet<String>();
		for (Product product : productList) {
			pidSet.add(product.getProductId());
		}
		for (String pid : pidSet) {
			int categoryId = Integer.valueOf(pid.substring(0, 4));
			Attribute attr = productDao.findProductAttr("price", categoryId);
			int priceAttr = attr.getId();
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
					if (product.getAttributeId() == priceAttr) {
						productView.setPrice(product.getValue());
					}
				}
			}
			pViewList.add(productView);
		}
		return pViewList;
	}

	@Override
	public List<ProductView> findProductInfo(String categorys) {
		if (Util.isEmpty(categorys)) {
			return null;
		}
		List<Product> productList = productDao.findProductInfoByCategorys(categorys);
		if (Util.isEmpty(productList)) {
			return null;
		}
		int priceAttr = 0;
		if (categorys.length() >= 4) {
			Attribute attr = productDao.findProductAttr("price", Integer.valueOf(categorys));
			priceAttr = attr.getId();
		}
		List<ProductView> pViewList = new ArrayList<ProductView>();
		Set<String> pidSet = new HashSet<String>();
		for (Product product : productList) {
			pidSet.add(product.getProductId());
		}
		for (String pid : pidSet) {
			if (categorys.length() < 4) {
				int categoryId = Integer.valueOf(pid.substring(0, 4));
				Attribute attr = productDao.findProductAttr("price", categoryId);
				priceAttr = attr.getId();
			}
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
					if (product.getAttributeId() == priceAttr) {
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

	@Override
	public List<Attribute> findAttributeByCategoryId(int categoryId) {
		return productDao.findAttributeByCategoryId(categoryId);
	}

	@Override
	public Attribute findAttributeById(int id) {
		return productDao.findAttributeById(id);
	}

	@Override
	public void saveAttribute(List<Attribute> list) throws DataAccessException {
		productDao.saveAttribute(list);
	}

	public Attribute saveAttribute(Attribute attribute) throws DataAccessException {
		return productDao.saveAttribute(attribute);
	}

	public void updateAttribute(Attribute attribute) throws DataAccessException {
		productDao.editAttribute(attribute);
	}

	public void deleteAttribute(String id) throws DataAccessException {
		productDao.deleteAttributeById(id);
	}

	public void deleteAllAttribute(String categoryId) throws DataAccessException {
		productDao.deleteAttributeByCategoryId(categoryId);
	}

	@Override
	public List<ProductView> findProductInfoByFilter(ProductFilter productFilter) {
		if (productFilter == null || Util.isEmpty(productFilter.getCategorys())) {
			return null;
		}
		Set<String> pidSet = findProductIdByFilter(productFilter);
		List<ProductView> result = new ArrayList<ProductView>();
		if (pidSet != null && pidSet.size() > 0) {
			List<String> pidList = new ArrayList(pidSet);
			int beginCount = productFilter.getOffset() + 1;
			int pageSize = productFilter.getPageSize();
			if (beginCount >= pidSet.size()) {
				return null;
			}
			if ((beginCount + pageSize) <= pidSet.size()) {
				pidList = pidList.subList(beginCount - 1, (beginCount - 1)+ pageSize);
			} else if ((beginCount + pageSize) > pidSet.size()) {
				pidList = pidList.subList(beginCount - 1, pidList.size() - 1);
			}
			StringBuilder pids = new StringBuilder();
			for (String pid : pidList) {
				pids.append(pid).append(",");
			}
			result = findProductInfoById(pids.toString());
		}
		return result;
	}

	@Override
	public int findProductCountByFilter(ProductFilter productFilter) {
		if (productFilter == null) {
			return 0;
		}
		Set<String> pidSet = findProductIdByFilter(productFilter);
		if (pidSet != null) {
			return pidSet.size();
		}
		return 0;
	}
	
	private Set<String> findProductIdByFilter(ProductFilter productFilter){
		if (productFilter == null) {
			return null;
		}
		Set<String> setPid = new HashSet<String>();
		List<Product> resultList = new ArrayList<Product>();
		// 品牌
		if (!Util.isEmpty(productFilter.getBrand())) {
			String[] brandArr = productFilter.getBrand().split("_");
			resultList = productDao.findProductInfo(Integer.valueOf(brandArr[0]), brandArr[1]);
			if (Util.isEmpty(resultList)) {
				return null;
			}
			for (Product p : resultList) {
				setPid.add(p.getProductId());
			}
		}
		// 颜色
		if (!Util.isEmpty(productFilter.getColor())) {
			String[] colorArr = productFilter.getColor().split("_");
			resultList = productDao.findProductInfo(Integer.valueOf(colorArr[0]), colorArr[1]);
			if (Util.isEmpty(resultList)) {
				return null;
			}
			for (Product p : resultList) {
				setPid.add(p.getProductId());
			}
		}
		// 价格
		if (!Util.isEmpty(productFilter.getPrice())) {
			String[] priceArr = productFilter.getPrice().split("_");
			resultList = productDao.findProductInfo(Integer.valueOf(priceArr[0]), priceArr[1]);
			if (Util.isEmpty(resultList)) {
				return null;
			}
			for (Product p : resultList) {
				setPid.add(p.getProductId());
			}
		}
		return setPid;
	}
}
