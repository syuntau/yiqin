package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.dao.IProductDao;
import com.yiqin.pojo.Category;
import com.yiqin.service.ProductManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class EditCategoryAction extends ActionSupport {

	private static final long serialVersionUID = 6362182987833824021L;
	private ProductManager productManager;
	private IProductDao productDao;
	private String id;
	private String name;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ProductManager getProductManager() {
		return productManager;
	}
	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public IProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	public String getList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			List<Category> categoryList = productManager.findCategoryInfo();
			if (Util.isEmpty(categoryList)) {
				result = UtilKeys.CODE_NO_RESULT;
				out.print(result);
				return null;
			}

			JSONArray jsArray = JSONArray.fromObject(categoryList);
			result = jsArray.toString();
			out.print(result);
			return null;
		} catch (IOException e1) {
			System.out.println("error in EditCategoryAction.getList for make printwriter");
			e1.printStackTrace();
			return null;
		}
	}

//	public String removeBrand() {
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("application/json;charset=UTF-8");
//		try {
//			PrintWriter out = response.getWriter();
//			String result = "";
//			if (Util.isEmpty(brandId) || !Util.isNumeric(brandId)) {
//				result = UtilKeys.CODE_ERR_PARAM;
//				out.print(result);
//				return null;
//			} else {
//				try {
//					productManager.deleteBrandById(brandId);
//					// remove all products by brand id ==> todo or undo
////					productManager.deleteProductByAttributeId(brandId);
//				} catch(DataAccessException dbe) {
//					System.out.println("error in EditCategoryAction.removeBrand for db exception");
//					dbe.printStackTrace();
//					out.print(UtilKeys.CODE_ERR_DB);
//					return null;
//				}
//
//				result = UtilKeys.CODE_SUCCESS;
//				out.print(result);
//			}
//		} catch (IOException e1) {
//			System.out.println("error in EditCategoryAction.removeBrand for io exception");
//			e1.printStackTrace();
//		}
//		return null;
//	}

	public String updateCatgory() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(id) || Util.isEmpty(name)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					List<Category> categoryList = productDao.findCategoryInfoByCategoryId(id);
					if (Util.isEmpty(categoryList)) {
						result = UtilKeys.CODE_NO_RESULT;
						out.print(result);
						return null;
					}
					Category category = categoryList.get(0);
					category.setName(name);
					category.setUpdateDate(new Date());
					productManager.editCategory(category);

					JSONObject json = JSONObject.fromObject(category);
					result = json.toString();
					out.print(result);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditCategoryAction.editCategory for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}
			}
		} catch (IOException e1) {
			System.out.println("error in EditCategoryAction.editCategory for io exception");
			e1.printStackTrace();
		}
		return null;
	}

	public String saveCategory() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(id) || Util.isEmpty(name)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					Category category = new Category();
					int cId = Integer.parseInt(id);
					int length = id.length();
					if (length == 4) {
						category.setLevel(3);
						category.setParentId(cId / 100);
					} else if (length == 2) {
						category.setLevel(2);
						category.setParentId(cId / 10);
					} else if (length == 1) {
						category.setLevel(1);
						category.setParentId(0);
					} else {
						System.out.println("error in EditCategoryAction.saveCategory for wrong category id 181");
						out.print(UtilKeys.CODE_ERR_PARAM);
						return null;
					}

					category.setId(cId);
					category.setName(name);
					Date date = new Date();
					category.setCreateDate(date);
					category.setUpdateDate(date);

					Integer categoryId = productManager.saveCategory(category);
					if (categoryId == null) {
						System.out.println("fail in EditCategoryAction.saveCategory");
						out.print(UtilKeys.CODE_ERR_DB);
						return null;
					}
					category.setId(categoryId);
					JSONObject json = JSONObject.fromObject(category);
					result = json.toString();
					out.print(result);
				} catch (NumberFormatException ne) {
					System.out.println("error in EditCategoryAction.saveCategory for wrong category id 197");
					ne.printStackTrace();
					out.print(UtilKeys.CODE_ERR_EXCEPTION);
					return null;
				} catch(DataAccessException dbe) {
					System.out.println("error in EditCategoryAction.saveCategory for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}
			}
		} catch (IOException e1) {
			System.out.println("error in EditCategoryAction.saveCategory for io exception");
			e1.printStackTrace();
		}
		return null;
	}
}
