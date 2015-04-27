package com.yiqin.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
		Map<String,Map<String,String>> productMap = findProductAllInfoByIds(pids);
		return productToProductView(productMap);
	}
	
	@Override
	public List<ProductView> findProductInfo(String categorys) {
		Map<String,Map<String,String>> productMap = findProductAllInfo(categorys);
		return productToProductView(productMap);
	}
	
	@Override
	public Map<String, Map<String, String>> findProductAllInfoByIds(String pids) {
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
		Set<String> pidSet = new HashSet<String>();
		for (Product product : productList) {
			pidSet.add(product.getProductId());
		}
		Map<String,Map<String,String>> resultMap = new HashMap<String,Map<String,String>>();
		Map<Integer, String> id_nameid= new HashMap<Integer, String>();
		
		Map<String,Map<Integer,String>> productMap = new HashMap<String,Map<Integer,String>>();
		for (String pid : pidSet) {
			Map<Integer,String> attrid_pvalue = new HashMap<Integer,String>();
			productMap.put(pid, attrid_pvalue);
			for(Product product : productList){
				if (pid.equals(product.getProductId())) {
					attrid_pvalue.put(product.getAttributeId(), product.getValue());
				}
			}
		}
		for (Map.Entry<String,Map<Integer,String>> entry : productMap.entrySet()) {
			int categoryId = Integer.valueOf(entry.getKey().substring(0, 4));
			id_nameid = initAttributeToMap(categoryId);
			
			Map<Integer,String> attrid_pvalueMap = entry.getValue();
			Map<String,String> nameid_pvalue = new HashMap<String,String>();
			resultMap.put(entry.getKey(), nameid_pvalue);
			for(Map.Entry<Integer, String> entrysub : attrid_pvalueMap.entrySet()){
				nameid_pvalue.put(id_nameid.get(entrysub.getKey()), entrysub.getValue());
			}
		}
		return resultMap;
	}
	
	@Override
	public Map<String,Map<String,String>> findProductAllInfo(String categorys){
		List<Product> productList = productDao.findProductInfoByCategorys(categorys);
		if (Util.isEmpty(productList)) {
			return null;
		}
		Set<String> pidSet = new HashSet<String>();
		for (Product product : productList) {
			pidSet.add(product.getProductId());
		}
		Map<String,Map<String,String>> resultMap = new HashMap<String,Map<String,String>>();
		Map<Integer, String> id_nameid= new HashMap<Integer, String>();
		
		if (categorys.length() >= 4) {
			id_nameid = initAttributeToMap(Integer.valueOf(categorys));
		}
		
		Map<String,Map<Integer,String>> productMap = new HashMap<String,Map<Integer,String>>();
		for (String pid : pidSet) {
			Map<Integer,String> attrid_pvalue = new HashMap<Integer,String>();
			productMap.put(pid, attrid_pvalue);
			for(Product product : productList){
				if (pid.equals(product.getProductId())) {
					attrid_pvalue.put(product.getAttributeId(), product.getValue());
				}
			}
		}
		for (Map.Entry<String,Map<Integer,String>> entry : productMap.entrySet()) {
			if (categorys.length() < 4) {
				int categoryId = Integer.valueOf(entry.getKey().substring(0, 4));
				id_nameid = initAttributeToMap(categoryId);
			}
			Map<Integer,String> attrid_pvalueMap = entry.getValue();
			Map<String,String> nameid_pvalue = new HashMap<String,String>();
			resultMap.put(entry.getKey(), nameid_pvalue);
			for(Map.Entry<Integer, String> entrysub : attrid_pvalueMap.entrySet()){
				nameid_pvalue.put(id_nameid.get(entrysub.getKey()), entrysub.getValue());
			}
		}
		return resultMap;
	}

	private List<ProductView> productToProductView(Map<String,Map<String,String>> productMap){
		List<ProductView> pViewList = new ArrayList<ProductView>();
		if(Util.isEmpty(productMap)){
			return pViewList;
		}
		for (Map.Entry<String,Map<String,String>> entry : productMap.entrySet()) {
			ProductView productView = new ProductView();
			productView.setProductId(entry.getKey());
			
			Map<String,String> nameid_pvalue = entry.getValue();
			
			productView.setProductName(nameid_pvalue.get("name"));
			productView.setPrice(nameid_pvalue.get("price"));
			productView.setColor(nameid_pvalue.get("color"));
			productView.setImgUrl(nameid_pvalue.get("imgurl"));
			
			pViewList.add(productView);
		}
		return pViewList;
	}
	
	/**
	 * Attribute 转为MAP
	 * 
	 * @param categoryId
	 *            分类ID
	 * @return Map<Integer, String> key=id value=nameID
	 */
	private Map<Integer, String> initAttributeToMap(int categoryId) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<Attribute> attrList = productDao.findAttributeByCategoryId(categoryId);
		for (Attribute attr : attrList) {
			map.put(attr.getId(), attr.getNameId());
		}
		return map;
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
	public List<Category> findCategoryInfo(int topCateId) {
		List<Category> categoryList = productDao.findCategoryInfo(topCateId);
		List<Category> tempCategory = new ArrayList<Category>();
		if (Util.isNotEmpty(categoryList)) {
			for (Category cate : categoryList) {
				int parentId = cate.getParentId();
				if (parentId == topCateId) {
					tempCategory.add(cate);
				}
			}
			handleSubCategory(categoryList, tempCategory);
		}
		return tempCategory;
	}

	@Override
	public List<Category> findTopCategoryInfo() {
		List<Category> categoryList = productDao.findTopCategoryInfo();
		return categoryList;
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
			if (beginCount > pidSet.size()) {
				return null;
			}
			if ((beginCount + pageSize) <= pidSet.size()) {
				pidList = pidList.subList(beginCount - 1, (beginCount - 1)+ pageSize);
			} else if ((beginCount + pageSize) > pidSet.size()) {
				pidList = pidList.subList(beginCount - 1, pidList.size());
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
		boolean flag = false;
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
			flag = true;
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
			flag = true;
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
			flag = true;
		}
		
		// 分类
		if(!flag){
			resultList = productDao.findProductInfoByCategorys(productFilter.getCategorys());
			if (Util.isEmpty(resultList)) {
				return null;
			}
			for (Product p : resultList) {
				setPid.add(p.getProductId());
			}
		}
		return setPid;
	}

	@Override
	public List<Attribute> findFilterAttribute(int categoryId) {
		List<Attribute> list = productDao.findAttributeByCategoryId(categoryId);
		List<Attribute> tempList = new ArrayList<Attribute>();
		if(!Util.isEmpty(list)){
			for(Attribute attr : list){
				String nameId = attr.getNameId();
				if("brand".equals(nameId) || "price".equals(nameId) || "color".equals(nameId)){
					tempList.add(attr);
				}
			}
		}
		if(!Util.isEmpty(tempList)){
			Util.sort(tempList);
		}
		return tempList;
	}
}
