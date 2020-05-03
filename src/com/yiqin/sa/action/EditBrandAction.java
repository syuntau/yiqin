package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.Brand;
import com.yiqin.service.ProductManager;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class EditBrandAction extends ActionSupport {

	private static final long serialVersionUID = 4873815139082418329L;
	private ProductManager productManager;
//	private String brandId;
	private Brand brand;

	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public ProductManager getProductManager() {
		return productManager;
	}
	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public String getList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			List<Brand> brandList = productManager.findAllBrand();
			if (Util.isEmpty(brandList)) {
				result = UtilKeys.CODE_NO_RESULT;
				out.print(result);
				return null;
			}

			JSONArray jsArray = JSONArray.fromObject(brandList);
			result = jsArray.toString();
			out.print(result);
			return null;
		} catch (IOException e1) {
			System.out.println("error in GetAttributeAction.getList for make printwriter");
			e1.printStackTrace();
			return null;
		}
	}

	public String removeBrand() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			HttpServletRequest request = ServletActionContext.getRequest();
			String brandId = request.getParameter("brandId");
			if (Util.isEmpty(brandId) || !Util.isNumeric(brandId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					productManager.deleteBrandById(brandId);
					// remove all products by brand id ==> todo or undo
//					productManager.deleteProductByAttributeId(brandId);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditBrandAction.removeBrand for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				result = UtilKeys.CODE_SUCCESS;
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in EditBrandAction.removeBrand for io exception");
			e1.printStackTrace();
		}
		return null;
	}

	public String updateBrand() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (brand == null) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					productManager.editBrand(brand);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditBrandAction.editBrand for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				JSONObject json = JSONObject.fromObject(brand);
				result = json.toString();
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in EditBrandAction.editBrand for io exception");
			e1.printStackTrace();
		}
		return null;
	}

	public String saveBrand() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (brand == null) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					Integer brandId = productManager.saveBrand(brand);
					if (brandId == null) {
						System.out.println("fail in EditBrandAction.saveBrand");
						out.print(UtilKeys.CODE_ERR_DB);
						return null;
					}
					brand.setId(brandId);
					JSONObject json = JSONObject.fromObject(brand);
					result = json.toString();
					out.print(result);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditBrandAction.saveBrand for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}
			}
		} catch (IOException e1) {
			System.out.println("error in EditBrandAction.saveBrand for io exception");
			e1.printStackTrace();
		}
		return null;
	}
}
