package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.dao.ISystemDao;
import com.yiqin.pojo.Conf;
import com.yiqin.service.ProductManager;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class EditPDIndexListAction extends ActionSupport {

	private static final long serialVersionUID = -2511091008112752769L;
	private ProductManager productManager;
	private ISystemDao systemDao;
	private String productId;

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public ProductManager getProductManager() {
		return productManager;
	}
	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public ISystemDao getSystemDao() {
		return systemDao;
	}

	public void setSystemDao(ISystemDao systemDao) {
		this.systemDao = systemDao;
	}

	public String getList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			Conf conf = systemDao.getConf("product_index_list");
			if (conf == null) {
				result = UtilKeys.CODE_NO_RESULT;
				out.print(result);
				return null;
			}

			String idList = conf.getValue();
			List<ProductView> proList = productManager.findProductInfoById(idList);
			JSONArray jsArray = JSONArray.fromObject(proList);
			result = jsArray.toString();
			out.print(result);
			return null;
		}  catch (DataAccessException de) {
			de.printStackTrace();
			return null;
		} catch (IOException e1) {
			System.out.println("error in EditPDIndexListAction.getList for make printwriter");
			e1.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Throwable t) {
			System.out.println(t.getMessage());
			t.printStackTrace();
			return null;
		}
	}

	public String removeProduct() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(productId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					Conf conf = systemDao.getConf("product_index_list");
					if (conf == null) {
						System.out.println("###### EditPDIndexListAction not-setted product index id list");
						result = UtilKeys.CODE_NO_RESULT;
						out.print(result);
						return null;
					}

					String idList = conf.getValue();
					System.out.println("###### EditPDIndexListAction product id list : " + idList + ", will delete id : " + productId);
					String[] idArr = idList.split(",");
					StringBuilder sb = new StringBuilder();
					for (String id : idArr) {
						if (!id.equals(productId)) {
							sb.append(",").append(id);
						}
					}
					System.out.println("###### EditPDIndexListAction deleted product id list : " + sb.toString());
					if (sb.length() > 0) {
						conf.setValue(sb.substring(1));
						systemDao.editConf(conf);
						System.out.println("###### EditPDIndexListAction deleted product id over");
					} else {
						systemDao.deleteConf(conf);
						System.out.println("###### EditPDIndexListAction deleted product index id list over");
					}

				} catch(DataAccessException dbe) {
					System.out.println("error in EditPDIndexListAction.removeProduct for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				result = UtilKeys.CODE_SUCCESS;
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in EditPDIndexListAction.removeProduct for io exception");
			e1.printStackTrace();
		}
		return null;
	}

	public String saveProduct() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(productId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
					List<ProductView> list = productManager.findProductInfoById(productId);
					if (Util.isEmpty(list)) {
						System.out.println("###### EditPDIndexListAction saveProduct no product, product id : " + productId);
						result = UtilKeys.CODE_NO_RESULT;
						out.print(result);
						return null;
					}

					Conf conf = systemDao.getConf("product_index_list");
					if (conf == null) {
						conf = new Conf("product_index_list", productId);
						systemDao.saveConf(conf);
						System.out.println("###### EditPDIndexListAction saveProduct save new product index list over");
					} else {
						String idList = conf.getValue();
						idList = idList + "," + productId;
						conf.setValue(idList);
						systemDao.editConf(conf);
						System.out.println("###### EditPDIndexListAction saveProduct edit product index list over");
					}

					JSONObject json = JSONObject.fromObject(list.get(0));
					result = json.toString();
					out.print(result);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditPDIndexListAction.saveProduct for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}
			}
		} catch (IOException e1) {
			System.out.println("error in EditPDIndexListAction.saveProduct for io exception");
			e1.printStackTrace();
		}
		return null;
	}
}
