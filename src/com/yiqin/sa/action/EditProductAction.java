package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Product;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class EditProductAction extends ActionSupport {

	private static final long serialVersionUID = -9120713921648008287L;
	private String cId;
	private String pId;
	private List<Product> pList;
	private ProductManager productManager;

	public String getCId() {
		return cId;
	}

	public void setCId(String cId) {
		this.cId = cId;
	}

	public String getPId() {
		return pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}

	public List<Product> getpList() {
		return pList;
	}

	public void setpList(List<Product> pList) {
		this.pList = pList;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String getItems() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(cId) || !Util.isNumeric(cId)) {
				result = UtilKeys.CODE_ERR_PARAM;
			} else {
				// 查询对应分类产品
				List<ProductView> productlist = productManager.findProductInfo(cId);
				if (Util.isEmpty(productlist)) {
					result = UtilKeys.CODE_NO_RESULT;
				} else {
					Util.sortProductView(productlist);
					JSONArray jsonArray = JSONArray.fromObject(productlist);
					result = jsonArray.toString();
				}
			}
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = UtilKeys.CODE_ERR_EXCEPTION;
			response.getWriter().print(result);
			return null;
		}
	}

	public String removeAll() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(cId) || !Util.isNumeric(cId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					productManager.deleteAllAttribute(cId);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditProductAction.removeAll for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				result = UtilKeys.CODE_SUCCESS;
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in EditProductAction.removeAll for io exception");
			e1.printStackTrace();
		}
		return null;
	}

	public String removeItem() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(pId) || !Util.isNumeric(pId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					productManager.deleteAttribute(pId);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditProductAction.removeItem for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				result = UtilKeys.CODE_SUCCESS;
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in EditProductAction.removeItem for io exception");
			e1.printStackTrace();
		}
		return null;
	}

	public String getItem() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(pId) || !Util.isNumeric(pId)) {
				result = UtilKeys.CODE_ERR_PARAM;
			} else {
				// 查询对应分类产品
				Map<String, Map<String, List<String>>> tempMap = productManager.findProductByIds(pId);
				if (Util.isEmpty(tempMap)) {
					result = UtilKeys.CODE_NO_RESULT;
				} else {
					Map<String, List<String>> pMap = tempMap.get(pId);
					JSONArray jsonArray = JSONArray.fromObject(pMap);
					result = jsonArray.toString();
				}
			}
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = UtilKeys.CODE_ERR_EXCEPTION;
			response.getWriter().print(result);
			return null;
		}
	}

	public String saveProduct() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (pList == null) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					productManager.saveProduct(pList);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditProductAction.saveProduct for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				List<ProductView> tempList = productManager.findProductInfoById(pId);
				JSONObject json = JSONObject.fromObject(tempList.get(0));
				result = json.toString();
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in EditProductAction.saveProduct for io exception");
			e1.printStackTrace();
		}
		return null;
	}
}
