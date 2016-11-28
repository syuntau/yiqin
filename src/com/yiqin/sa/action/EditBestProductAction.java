package com.yiqin.sa.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.BestProduct;
import com.yiqin.pojo.CommonProduct;
import com.yiqin.pojo.User;
import com.yiqin.service.ProductManager;
import com.yiqin.service.UserManager;
import com.yiqin.shop.bean.ProductView;
import com.yiqin.shop.bean.UserView;
import com.yiqin.util.Util;
import com.yiqin.util.UtilKeys;

public class EditBestProductAction extends ActionSupport {
	private static final long serialVersionUID = 7163225413110248267L;
	private UserManager userManager;
	private ProductManager productManager;
	private String cId;
	private String pIds;
	private String userId;

	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	public ProductManager getProductManager() {
		return productManager;
	}
	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}
	public String getCId() {
		return cId;
	}
	public void setCId(String cId) {
		this.cId = cId;
	}
	public String getPIds() {
		return pIds;
	}
	public void setPIds(String pIds) {
		this.pIds = pIds;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			List<User> userList = userManager.findAll();
			if (Util.isEmpty(userList)) {
				result = UtilKeys.CODE_NO_RESULT;
				out.print(result);
				return null;
			}
			List<UserView> showUserList = new ArrayList<UserView>();
			for (User user : userList) {
				UserView userView = new UserView(user);
				showUserList.add(userView);
			}
			JSONArray jsArray = JSONArray.fromObject(showUserList);
			result = jsArray.toString();
			out.print(result);
			return null;
		} catch (Exception e1) {
			System.out.println("error in EditBestProductAction.getUserList for make printwriter");
			e1.printStackTrace();
			return null;
		}
	}

	public String saveBestProduct() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(cId) || Util.isEmpty(pIds)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
//				try {
//					BestProduct bestProduct = productManager.findBestProductByCategoryId(userId, cId);
//					if (bestProduct == null) {
//						bestProduct = new BestProduct();
//						bestProduct.setUserId(userId);
//						bestProduct.setCategoryId(Integer.parseInt(cId));
//					}
//					bestProduct.setProductId(pIds);
//					productManager.saveBestProduct(bestProduct);
//					result = UtilKeys.CODE_SUCCESS;
//					out.print(result);
//				} catch(DataAccessException dbe) {
//					System.out.println("error in EditBestProductAction.saveBestProduct for db exception");
//					dbe.printStackTrace();
//					out.print(UtilKeys.CODE_ERR_DB);
//					return null;
//				}
				try {
					List<CommonProduct> dbList = productManager.findCommonProductByCategoryId(userId, cId);
					List<CommonProduct> saveList = new ArrayList<CommonProduct>();
					if (Util.isEmpty(dbList)) {
						for (String pId : pIds.split(",")) {
							CommonProduct commonProduct = new CommonProduct();
							commonProduct.setUserId(userId);
							commonProduct.setCategoryId(Integer.parseInt(cId));
							commonProduct.setProductId(pId);
							commonProduct.setCount(1);
							saveList.add(commonProduct);
						}
					} else {
						Map<String, Integer> comcommonProCountMap = new HashMap<String, Integer>();
						for (CommonProduct commonProduct : dbList) {
							comcommonProCountMap.put(commonProduct.getProductId(), commonProduct.getCount());
						}
						for (String pId : pIds.split(",")) {
							Integer count = comcommonProCountMap.get(pId);
							if (count == null) {
								CommonProduct commonProduct = new CommonProduct();
								commonProduct.setUserId(userId);
								commonProduct.setCategoryId(Integer.parseInt(cId));
								commonProduct.setProductId(pId);
								commonProduct.setCount(1);
								saveList.add(commonProduct);
							} else {
								CommonProduct commonProduct = new CommonProduct();
								commonProduct.setUserId(userId);
								commonProduct.setCategoryId(Integer.parseInt(cId));
								commonProduct.setProductId(pId);
								commonProduct.setCount(count + 1);
								saveList.add(commonProduct);
							}
						}
					}
					productManager.saveCommonProductList(saveList);
					result = UtilKeys.CODE_SUCCESS;
					out.print(result);
					System.out.println("EditBestProductAction.saveBestProduct userId : " + userId + ", cId : " + cId + ", pIds : " + pIds);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditBestProductAction.saveBestProduct for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}
			}
		} catch (IOException e1) {
			System.out.println("error in EditBestProductAction.saveBestProduct for io exception");
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getItems() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(cId) || !Util.isNumeric(cId) || Util.isEmpty(userId)) {
				result = UtilKeys.CODE_ERR_PARAM;
			} else {
				// 查询对应分类产品
				List<ProductView> productlist = productManager.findProductInfo(cId);
				
				if (Util.isEmpty(productlist)) {
					result = UtilKeys.CODE_NO_RESULT;
				} else {
//					Map<String, List<String>> map = productManager.findBestProductByUserId(userId);
//					if (!Util.isEmpty(map)) {
//						List<String> pdList = map.get(cId);
//						if (!Util.isEmpty(pdList)) {
//							Map<String, Integer> temp = new HashMap<String, Integer>();
//							int i = 0;
//							for (ProductView pv : productlist) {
//								temp.put(pv.getProductName(), i++);
//								pv.setCheck("off");
//							}
//							String[] pdArr = pdList.get(1).split(",");
//							for (String name : pdArr) {
//								Integer idx = temp.get(name);
//								if (idx != null) {
//									productlist.get(idx).setCheck("on");
//								}
//							}
//						}
//					}
//					Util.sortProductView(productlist);
//					JSONArray jsonArray = JSONArray.fromObject(productlist);
//					result = jsonArray.toString();


					Map<String, List<String>> map = productManager.findBestProductByUserId(userId);
					if (!Util.isEmpty(map)) {
						List<String> pdList = map.get(cId);
						if (!Util.isEmpty(pdList)) {
							Map<String, Integer> temp = new HashMap<String, Integer>();
							int i = 0;
							for (ProductView pv : productlist) {
								temp.put(pv.getProductName(), i++);
								pv.setCheck("off");
							}
							String[] pdArr = pdList.get(1).split(",");
							for (String name : pdArr) {
								Integer idx = temp.get(name);
								if (idx != null) {
									productlist.get(idx).setCheck("on");
								}
							}
						}
					}
					Util.sortProductViewByCount(productlist);
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

	public String getBestProductList() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			Map<String, List<String>> map = productManager.findBestProductByUserId(userId);
			if (Util.isEmpty(map)) {
				result = UtilKeys.CODE_NO_RESULT;
				out.print(result);
				return null;
			}
			JSONArray jsArray = JSONArray.fromObject(map);
			result = jsArray.toString();
			System.out.println("result : " + result);
			out.print(result);
			return null;
		} catch (Exception e1) {
			System.out.println("error in EditBestProductAction.getBestProductList for make printwriter");
			e1.printStackTrace();
			return null;
		}
	}

	public String removeAll() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(userId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
//					productManager.deleteAllBestProduct(userId);
					productManager.deleteAllCommonProduct(userId);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditBestProductAction.removeAll for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				result = UtilKeys.CODE_SUCCESS;
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in EditBestProductAction.removeAll for io exception");
			e1.printStackTrace();
		} catch (Exception e) {
			System.out.println("error in EditBestProductAction.removeAll for exception");
			e.printStackTrace();
		}
		return null;
	}

	public String removeItem() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			String result = "";
			if (Util.isEmpty(cId) || !Util.isNumeric(cId) || Util.isEmpty(userId)) {
				result = UtilKeys.CODE_ERR_PARAM;
				out.print(result);
				return null;
			} else {
				try {
//					productManager.deleteBestProduct(userId, cId);
					productManager.deleteCommonProduct(userId, cId);
				} catch(DataAccessException dbe) {
					System.out.println("error in EditBestProductAction.removeItem for db exception");
					dbe.printStackTrace();
					out.print(UtilKeys.CODE_ERR_DB);
					return null;
				}

				result = UtilKeys.CODE_SUCCESS;
				out.print(result);
			}
		} catch (IOException e1) {
			System.out.println("error in EditBestProductAction.removeItem for io exception");
			e1.printStackTrace();
		} catch (Exception e) {
			System.out.println("error in EditBestProductAction.removeItem for exception");
			e.printStackTrace();
		}
		return null;
	}
}
