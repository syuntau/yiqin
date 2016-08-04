package com.yiqin.serviceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.yiqin.dao.IProductDao;
import com.yiqin.dao.IUserDao;
import com.yiqin.pojo.Attribute;
import com.yiqin.pojo.BestProduct;
import com.yiqin.pojo.Brand;
import com.yiqin.pojo.Category;
import com.yiqin.pojo.Product;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductFilter;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.BrandUtil;
import com.yiqin.util.CategoryUtil;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class ProductManagerImpl implements ProductManager {

	private IProductDao productDao;
	
	private IUserDao userDao;

	public IProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}
	
	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<ProductView> findProductInfoById(String userId, String pids) {
		Map<String,Map<String,String>> productMap = findProductAllInfoByIds(pids);
		return productToProductView(userId, productMap);
	}
	
	@Override
	public List<ProductView> findProductInfoById(String pids) {
		Map<String,Map<String,String>> productMap = findProductAllInfoByIds(pids);
		return productToProductView(productMap);
	}
	
	@Override
	public List<ProductView> findProductInfo(String userId, String categorys) {
		Map<String,Map<String,String>> productMap = findProductAllInfo(categorys);
		return productToProductView(userId, productMap);
	}
	
	@Override
	public List<ProductView> findProductInfo(String categorys) {
		Map<String,Map<String,String>> productMap = findProductAllInfo(categorys);
		return productToProductView(productMap);
	}
	
	@Override
	public List<ProductView> findBestProductInfo(String userId,String categoryId, int offset, int pageSize) {
		Set<String> pidSet = findProductIdsByUserId(userId,categoryId);
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
			result = findProductInfoById(userId,pids.toString());
		}
		return result;
	}
	
	@Override
	public int findBestProductCount(String userId, String categoryId) {
		Set<String> pidSet = findProductIdsByUserId(userId,categoryId);
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
		
		Set<String> cateId4wei = new HashSet<String>();
		Map<String,Map<Integer,String>> productMap = new HashMap<String,Map<Integer,String>>();
		for (String pid : pidSet) {
			Map<Integer,String> attrid_pvalue = new HashMap<Integer,String>();
			productMap.put(pid, attrid_pvalue);
			cateId4wei.add(pid.substring(0, 4));
			for(Product product : productList){
				if (pid.equals(product.getProductId())) {
					attrid_pvalue.put(product.getAttributeId(), product.getValue());
				}
			}
		}
		Map<String, Map<Integer, List<String>>> attrMap = getAttributeMapByCategory4WeiId(cateId4wei);
		
		for (Map.Entry<String,Map<Integer,String>> entry : productMap.entrySet()) {
			id_nameid = attrMap.get(entry.getKey().substring(0, 4));
			
			Map<Integer,String> attrid_pvalueMap = entry.getValue();
			Map<String,String> nameid_pvalue = new HashMap<String,String>();
			resultMap.put(entry.getKey(), nameid_pvalue);
			for(Map.Entry<Integer, String> entrysub : attrid_pvalueMap.entrySet()){
				String nameId = id_nameid.get(entrysub.getKey()).get(0);
				String value = entrysub.getValue();
				if("brandId".equals(nameId)){
					value = getBrandShowName(Integer.valueOf(value));
				}
				nameid_pvalue.put(nameId, value);
			}
		}
		return resultMap;
	}
	
	private Map<String, Map<Integer, List<String>>> getAttributeMapByCategory4WeiId(Set<String> cate4WeiId) {
		Map<String, Map<Integer, List<String>>> map = new HashMap<String, Map<Integer, List<String>>>();
		if (cate4WeiId == null || cate4WeiId.size() == 0) {
			return map;
		}
		for (String c4id : cate4WeiId) {
			map.put(c4id, initAttributeToMap(Integer.valueOf(c4id)));
		}
		return map;
	}
	
	private String getBrandShowName(Integer brandId) {
		String value = "";
		Brand brand = findProductBrandByBrandId(brandId);
		String cnName = brand.getNameCn();
		String enName = brand.getNameEn();
		if (Util.isNotEmpty(cnName) && Util.isNotEmpty(enName)) {
			value = cnName + "(" + enName + ")";
		} else if (Util.isNotEmpty(cnName)) {
			value = cnName;
		} else {
			value = enName;
		}
		return value;
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
		
		Set<String> cateId4wei = new HashSet<String>();
		Map<String,Map<Integer,String>> productMap = new HashMap<String,Map<Integer,String>>();
		for (String pid : pidSet) {
			Map<Integer,String> attrid_pvalue = new HashMap<Integer,String>();
			productMap.put(pid, attrid_pvalue);
			cateId4wei.add(pid.substring(0, 4));
			for(Product product : productList){
				if (pid.equals(product.getProductId())) {
					attrid_pvalue.put(product.getAttributeId(), product.getValue());
				}
			}
		}
		Map<String, Map<Integer, List<String>>> attrMap = getAttributeMapByCategory4WeiId(cateId4wei);
		for (Map.Entry<String,Map<Integer,String>> entry : productMap.entrySet()) {
			id_nameid = attrMap.get(entry.getKey().substring(0, 4));
//			int categoryId = Integer.valueOf(entry.getKey().substring(0, 4));
//			id_nameid = initAttributeToMap(categoryId);
			
			Map<Integer,String> attrid_pvalueMap = entry.getValue();
			attrid_pvalueMap = Util.sort(attrid_pvalueMap);
			Map<String,String> nameid_pvalue = new LinkedHashMap<String,String>();
			resultMap.put(entry.getKey(), nameid_pvalue);
			for(Map.Entry<Integer, String> entrysub : attrid_pvalueMap.entrySet()){
				String name = id_nameid.get(entrysub.getKey()).get(1);
				String value = entrysub.getValue();
				if("品牌".equals(name)){
					value = getBrandShowName(Integer.valueOf(value));
				}
				nameid_pvalue.put(name, value);
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
		
//		if (categorys.length() >= 4) {
//			id_nameid = initAttributeToMap(Integer.valueOf(categorys));
//		}
		
		Set<String> cateId4wei = new HashSet<String>();
		Map<String,Map<Integer,String>> productMap = new HashMap<String,Map<Integer,String>>();
		for (String pid : pidSet) {
			Map<Integer,String> attrid_pvalue = new HashMap<Integer,String>();
			productMap.put(pid, attrid_pvalue);
			cateId4wei.add(pid.substring(0, 4));
			for(Product product : productList){
				if (pid.equals(product.getProductId())) {
					attrid_pvalue.put(product.getAttributeId(), product.getValue());
				}
			}
		}
		Map<String, Map<Integer, List<String>>> attrMap = getAttributeMapByCategory4WeiId(cateId4wei);
		
		for (Map.Entry<String,Map<Integer,String>> entry : productMap.entrySet()) {
			id_nameid = attrMap.get(entry.getKey().substring(0, 4));
//			if (categorys.length() < 4) {
//				int categoryId = Integer.valueOf(entry.getKey().substring(0, 4));
//				id_nameid = initAttributeToMap(categoryId);
//			}
			Map<Integer,String> attrid_pvalueMap = entry.getValue();
			Map<String,String> nameid_pvalue = new HashMap<String,String>();
			resultMap.put(entry.getKey(), nameid_pvalue);
			for(Map.Entry<Integer, String> entrysub : attrid_pvalueMap.entrySet()){
				String nameId = id_nameid.get(entrysub.getKey()).get(0);
				String value = entrysub.getValue();
				if("brandId".equals(nameId)){
					value = getBrandShowName(Integer.valueOf(value));
				}
				nameid_pvalue.put(nameId, value);
			}
		}
		return resultMap;
	}

	private List<ProductView> productToProductView(String userId, Map<String,Map<String,String>> productMap){
		List<ProductView> pViewList = new ArrayList<ProductView>();
		if(Util.isEmpty(productMap)){
			return pViewList;
		}
		float zhekou = (float) 1;
		if(Util.isNotEmpty(userId)){
			UserConf userConf = userDao.findUserConfInfo(userId, "zhekou");
			if (userConf != null) {
				zhekou =  Float.valueOf(userConf.getValue());
			}
		}
		for (Map.Entry<String,Map<String,String>> entry : productMap.entrySet()) {
			ProductView productView = new ProductView();
			productView.setProductId(entry.getKey());
			
			Map<String,String> nameid_pvalue = entry.getValue();
			String price = nameid_pvalue.get("price");
			String zhekouPrice = new DecimalFormat("#########.00").format(Float.valueOf(price)*zhekou);
			
			productView.setProductName(nameid_pvalue.get("name"));
			productView.setPrice(price);
			productView.setColor(nameid_pvalue.get("color"));
			productView.setZhekouPrice(zhekouPrice);
			productView.setImgUrl(nameid_pvalue.get("imageUrl"));
			
			pViewList.add(productView);
		}
		return pViewList;
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
	
	@Override
	public List<Category> findCategoryInfoForBest(String userId) {
		List<BestProduct> bestList = productDao.findBestProductByUserId(userId);
		if(Util.isNotEmpty(bestList)){
			Set<String> topId = new HashSet<String>();
			StringBuilder bestCateIds = new StringBuilder();
			for(BestProduct bp : bestList){
				bestCateIds.append(bp.getCategoryId()).append(",");
				topId.add(bp.getCategoryId().toString().substring(0, 2));
			}
			StringBuilder cids = new StringBuilder();
			for (String cid : topId) {
				cids.append(cid).append(",");
			}
			List<Category> bestCategorys = productDao.findCategoryInfoByCategoryId(bestCateIds.toString());
			List<Category> categorytops = productDao.findCategoryInfoByCategoryId(cids.toString());
			if(Util.isNotEmpty(bestCategorys)){
				bestCategorys.addAll(categorytops);
				handleSubCategory(bestCategorys, categorytops);
			}
			return categorytops;
		}
		return null;
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
	public List<Category> findCategoryInfoForBest(int topCateId, String userId) {
		List<BestProduct> bestList = productDao.findBestProductByUserId(userId);
		if(Util.isNotEmpty(bestList)){
			Set<String> topId = new HashSet<String>();
			StringBuilder bestCateIds = new StringBuilder();
			for(BestProduct bp : bestList){
				if(bp.getCategoryId().toString().startsWith(String.valueOf(topCateId))){
					bestCateIds.append(bp.getCategoryId()).append(",");
					topId.add(String.valueOf(bp.getCategoryId()).substring(0, 2));
				}
			}
			if(Util.isEmpty(bestCateIds.toString())){
				return null;
			}
			StringBuilder cids = new StringBuilder();
			for (String cid : topId) {
				cids.append(cid).append(",");
			}
			List<Category> bestCategorys = productDao.findCategoryInfoByCategoryId(bestCateIds.toString());
			List<Category> categorytops = productDao.findCategoryInfoByCategoryId(cids.toString());
			if(Util.isNotEmpty(bestCategorys)){
				bestCategorys.addAll(categorytops);
				handleSubCategory(bestCategorys, categorytops);
			}
			return categorytops;
		}
		return null;
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
	public void deleteProducts(String pids) throws DataAccessException {
		productDao.deleteProductByIds(pids);
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
			result = findProductInfoById(productFilter.getUserId(), pids.toString());
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
	
	private Set<String> jiaoJiProductId(Set<String> prevPid, Set<String> nextPid){
		Set<String> result = new HashSet<String>();
		if(prevPid.size() > 0 && nextPid.size() >0){
			for (String pid : prevPid) {
				if(nextPid.contains(pid)){
					result.add(pid);
				}
			}
		}
		return result;
	}
	
	private Set<String> findProductIdByFilter(ProductFilter productFilter){
		if (productFilter == null) {
			return null;
		}
		Set<String> resultPid = new HashSet<String>();
		Set<String> prevPid = new HashSet<String>();
		List<Product> resultList = new ArrayList<Product>();
		boolean flag = false;
		
		String filterStr = productFilter.getFilterStr();
		if(Util.isNotEmpty(filterStr)){
			flag = true;
			String[] filterArr = filterStr.split(",");
			int fcount = filterArr.length;
			
			//首先用第一个条件去查询结果
			String[] firstArr = filterArr[0].split("_");
			resultList = productDao.findProductInfo(Integer.valueOf(firstArr[0]), firstArr[1]);
			if (Util.isEmpty(resultList)) {
				return null;
			}
			for (Product p : resultList) {
				prevPid.add(p.getProductId());
			}
			
			//从第二个条件开始循环去查询，并同时和上一次的取交集
			if(fcount > 1){
				for (int i = 1; i < fcount; i++) {
					String[] filter = filterArr[i].split("_");
					resultList = productDao.findProductInfo(Integer.valueOf(filter[0]), filter[1]);
					if (Util.isEmpty(resultList)) {
						return null;
					}
					Set<String> nextPid = new HashSet<String>();
					for (Product p : resultList) {
						nextPid.add(p.getProductId());
					}
					prevPid = jiaoJiProductId(prevPid, nextPid);
					if (prevPid.size() <= 0) {
						return null;
					}
				}
			}
			resultPid = prevPid;
		}
		// 分类
		if(!flag){
			resultList = productDao.findProductInfoByCategorys(productFilter.getCategorys());
			if (Util.isEmpty(resultList)) {
				return null;
			}
			for (Product p : resultList) {
				resultPid.add(p.getProductId());
			}
		}
		return resultPid;
	}
	
	private Set<String> findProductIdsByUserId(String userId,String cateId){
		if(Util.isEmpty(userId)){
			return null;
		}
		List<BestProduct> bestProducts = productDao.findBestProductByTopCateId(userId, cateId);
		if(Util.isNotEmpty(bestProducts)){
			Set<String> set = new HashSet<String>();
			for(BestProduct bp : bestProducts){
				String productIds = bp.getProductId();
				if(Util.isNotEmpty(productIds)){
					 String[] ids = productIds.split(",");
					 set.addAll(Arrays.asList(ids));
				}
			}
			return set;
		}
		return null;
	}

	@Override
	public List<Attribute> findFilterAttribute(int categoryId) {
		List<Attribute> list = productDao.findAttributeByCategoryId(categoryId);
		List<Attribute> tempList = new ArrayList<Attribute>();
		if(!Util.isEmpty(list)){
			for(Attribute attr : list){
				if (attr.getFilter() == 1) {
					String nameId = attr.getNameId();
					if("brandId".equals(nameId)){
						String showValue = attr.getShowValue();
						String [] svArr = showValue.split(",");
						StringBuilder sb = new StringBuilder();
						for(String sv : svArr){
							String bName = getBrandShowName(Integer.valueOf(sv));
							sb.append(bName).append(",");
						}
						if(Util.isNotEmpty(sb.toString())){
							showValue = sb.substring(0,sb.length()-1);
						}
						attr.setShowValue(showValue);
					}
					tempList.add(attr);
				}
			}
		}
		if(!Util.isEmpty(tempList)){
			Util.sort(tempList);
		}
		return tempList;
	}
	
	@Override
	public Map<String, List<String>> findBestProductByUserId(String userId) {
		List<BestProduct> list = productDao.findBestProductByUserId(userId);
		if (Util.isNotEmpty(list)) {
			Map<String, List<String>> result = new LinkedHashMap<String, List<String>>();
			for (BestProduct bd : list) {
				List<String> temp = new ArrayList<String>();
				String categoryName = CategoryUtil.getCategoryName(bd.getCategoryId());
				temp.add(categoryName);
				List<ProductView> productList = findProductInfoById(userId, bd.getProductId());
				StringBuilder name = new StringBuilder();
				for (ProductView pv : productList) {
					name.append(",").append(pv.getProductName());
				}
				if (name.length() > 0) {
					temp.add(name.substring(1));
				}
				result.put(String.valueOf(bd.getCategoryId()), temp);
			}
			return result;
		}
		return null;
	}

	@Override
	public BestProduct findBestProductByCategoryId(String userId, String categoryId) {
		return productDao.findBestProductByCategoryId(userId, categoryId);
	}

	@Override
	public void saveBestProduct(BestProduct bestProduct)
			throws DataAccessException {
		productDao.saveBestProduct(bestProduct);
	}

	@Override
	public void deleteAllBestProduct(String userId) throws DataAccessException {
		productDao.deleteBestProductByUserId(userId);
	}

	@Override
	public void deleteBestProduct(String userId, String categoryId)
			throws DataAccessException {
		productDao.deleteBestProductBycategoryId(userId, categoryId);
	}

	@Override
	public Brand findProductBrandByBrandId(int brandId) {
		Brand brand = BrandUtil.getBrand(brandId);
		if (brand == null) {
			BrandUtil.init(productDao);
			brand = BrandUtil.getBrand(brandId);
		}
		return brand;
	}

	@Override
	public List<Brand> findAllBrand() {
		List<Brand> brandList = BrandUtil.getBrandList();
		if (Util.isEmpty(brandList)) {
			BrandUtil.init(productDao);
			brandList = BrandUtil.getBrandList();
		}
		return brandList;
	}

	@Override
	public void deleteBrandById(String id) throws DataAccessException {
		productDao.deleteBrandById(id);
		BrandUtil.reInit(productDao);
	}

	@Override
	public void editBrand(Brand brand) throws DataAccessException {
		productDao.editBrand(brand);
		BrandUtil.reInit(productDao);
	}

	@Override
	public Integer saveBrand(Brand brand) throws DataAccessException {
		Integer brandId = productDao.saveBrand(brand);
		if (brandId != null) {
			BrandUtil.reInit(productDao);
		}

		return brandId;
	}

	public void editCategory(Category category) throws DataAccessException {
		productDao.editCategory(category);
		CategoryUtil.reInit(this);
	}

	public Integer saveCategory(Category category) throws DataAccessException {
		Integer categoryId = productDao.saveCategory(category);
		if (categoryId != null) {
			CategoryUtil.reInit(this);
		}

		return categoryId;
	}
}
