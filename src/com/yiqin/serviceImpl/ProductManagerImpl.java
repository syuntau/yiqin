package com.yiqin.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.yiqin.dao.IProductDao;
import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.BestProduct;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.Product;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductFilter;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

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
	public List<ProductView> findBestProductInfo(String userId, int offset, int pageSize) {
		Set<String> pidSet = findProductIdsByUserId(userId);
		List<ProductView> result = new ArrayList<ProductView>();
		if (pidSet != null && pidSet.size() > 0) {
			List<String> pidList = new ArrayList(pidSet);
			int beginCount = offset + 1;
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
	public int findBestProductCount(String userId) {
		Set<String> pidSet = findProductIdsByUserId(userId);
		if(pidSet != null){
			return pidSet.size();
		}
		return 0;
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
		Map<Integer, List<String>> id_nameid= new HashMap<Integer, List<String>>();
		
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
				nameid_pvalue.put(id_nameid.get(entrysub.getKey()).get(0), entrysub.getValue());
			}
		}
		return resultMap;
	}
	
	@Override
	public Map<String, Map<String, String>> findProductDetailByIds(String pids) {
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
		Map<Integer, List<String>> id_nameid= new HashMap<Integer, List<String>>();
		
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
				nameid_pvalue.put(id_nameid.get(entrysub.getKey()).get(1), entrysub.getValue());
			}
		}
		return resultMap;
	}

	@Override
	public Map<String, Map<String, List<String>>> findProductByIds(String pid) {
		if (Util.isEmpty(pid)) {
			return null;
		}
		List<Product> productList = productDao.findProductInfoById(pid);
		if(Util.isEmpty(productList)){
			return null;
		}
		Map<String,Map<String, List<String>>> resultMap = new HashMap<String,Map<String,List<String>>>();
		Map<Integer, List<String>> id_nameid= new HashMap<Integer, List<String>>();
		
		Map<String,Map<Integer, List<String>>> productMap = new HashMap<String,Map<Integer,List<String>>>();
		Map<Integer,List<String>> attrid_pvalue = new HashMap<Integer,List<String>>();
		productMap.put(pid, attrid_pvalue);
		for(Product product : productList){
			if (pid.equals(product.getProductId())) {
				List<String> list = new ArrayList<String>();
				list.add(product.getValue());
				list.add(String.valueOf(product.getId()));
				attrid_pvalue.put(product.getAttributeId(), list);
			}
		}
		for (Map.Entry<String,Map<Integer,List<String>>> entry : productMap.entrySet()) {
			int categoryId = Integer.valueOf(entry.getKey().substring(0, 4));
			id_nameid = initAttributeToMap(categoryId);
			
			Map<Integer,List<String>> attrid_pvalueMap = entry.getValue();
			Map<String,List<String>> nameid_pvalue = new HashMap<String,List<String>>();
			resultMap.put(entry.getKey(), nameid_pvalue);
			for(Map.Entry<Integer, List<String>> entrysub : id_nameid.entrySet()){
				List<String> list = new ArrayList<String>();
				list.add(entrysub.getValue().get(1));
				List<String> proList = attrid_pvalueMap.get(entrysub.getKey());
				if (Util.isEmpty(proList)) {
					// 如果商品尚未设定 属性，则做一套空记录
					proList = new ArrayList<String>();
					proList.add(UtilKeys.BLANK);
					proList.add(UtilKeys.BLANK_ZERO);
				}
				list.addAll(proList);
				// map 构造：
				// key 为 属性ID（即：attribute 的 nameId）
				// value 为List，List 目前有3个值：
				// 属性名称（即：attribute 的 name）
				// 商品对应属性的值（例如：属性为 颜色 的时候，对应值为：黑色）
				// 商品记录在Product表中的ID，此ID用于更新Product表时使用，如果ID为"empty"，表示商品尚未设定该属性
				nameid_pvalue.put(String.valueOf(entrysub.getKey()), list);
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
		Map<Integer, List<String>> id_nameid= new HashMap<Integer, List<String>>();
		
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
				nameid_pvalue.put(id_nameid.get(entrysub.getKey()).get(0), entrysub.getValue());
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
			productView.setImgUrl(nameid_pvalue.get("imageUrl"));
			
			pViewList.add(productView);
		}
		return pViewList;
	}
	
	/**
	 * Attribute 转为MAP
	 * 
	 * @param categoryId
	 *            分类ID
	 * @return Map<Integer, List<String>> key=id value=attibute list(0:nameId, 1:name)
	 */
	private Map<Integer, List<String>> initAttributeToMap(int categoryId) {
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
		List<Attribute> attrList = productDao.findAttributeByCategoryId(categoryId);
		for (Attribute attr : attrList) {
			List<String> list = new ArrayList<String>();
			list.add(attr.getNameId());
			list.add(attr.getName());
			map.put(attr.getId(), list);
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

	@Override
	public int saveAttribute(Attribute attribute) throws DataAccessException {
		return productDao.saveAttribute(attribute);
	}

	@Override
	public void updateAttribute(Attribute attribute) throws DataAccessException {
		productDao.editAttribute(attribute);
	}

	@Override
	public void deleteAttribute(String id) throws DataAccessException {
		productDao.deleteAttributeById(id);
	}

	@Override
	public void deleteAllAttribute(String categoryId) throws DataAccessException {
		productDao.deleteAttributeByCategoryId(categoryId);
	}

	@Override
	public void deleteAllProduct(String categoryId) throws DataAccessException {
		productDao.deleteProductByCategoryId(categoryId);
	}

	@Override
	public void deleteProduct(String productId) throws DataAccessException {
		productDao.deleteProductById(productId);
	}

	@Override
	public void deleteProductByAttributeId(String attributeId) throws DataAccessException {
		productDao.deleteProductByAttributeId(attributeId);
	}

	@Override
	public void saveProduct(List<Product> list) throws DataAccessException {
		productDao.saveProduct(list);
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
	
	private Set<String> findProductIdsByUserId(String userId){
		if(Util.isEmpty(userId)){
			return null;
		}
		BestProduct bestProduct = productDao.findBestProductByUserId(userId);
		if(bestProduct != null){
			String productIds = bestProduct.getProductId();
			if(Util.isNotEmpty(productIds)){
				 if (productIds.contains(",")) {
					if (productIds.startsWith(",")) {
						productIds = productIds.substring(1);
					}
					if (productIds.endsWith(",")) {
						productIds = productIds.substring(0, productIds.length() - 1);
					}
				 }
				 String[] ids = productIds.split(",");
				 Set<String> set = new HashSet<>(Arrays.asList(ids));
				 return set;
			}
		}
		return null;
	}

	@Override
	public List<Attribute> findFilterAttribute(int categoryId) {
		List<Attribute> list = productDao.findAttributeByCategoryId(categoryId);
		List<Attribute> tempList = new ArrayList<Attribute>();
		if(!Util.isEmpty(list)){
			for(Attribute attr : list){
				String nameId = attr.getNameId();
				if("brandId".equals(nameId) || "price".equals(nameId) || "color".equals(nameId)){
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
