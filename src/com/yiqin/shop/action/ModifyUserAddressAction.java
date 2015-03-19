package com.yiqin.shop.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.shop.pojo.UserConf;
import com.yiqin.shop.service.UserManager;
import com.yiqin.util.Util;

/**
 * 更新或新增收货地址
 * 
 * @author liujun
 *
 */
public class ModifyUserAddressAction extends ActionSupport {

	private static final long serialVersionUID = 782422695301557740L;

	private UserManager userManager;

	// 地址信息
	private String address;

	// 联系电话
	private String telephone;

	// 收货人姓名
	private String userName;

	// 地址属性
	private String attribute;

	// 之前地址属性
	private String oldAttribute;

	// 保存或更新
	private String saveOrUpdate;

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getUserName() {
		return userName;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getOldAttribute() {
		return oldAttribute;
	}

	public void setOldAttribute(String oldAttribute) {
		this.oldAttribute = oldAttribute;
	}

	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}

	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(address) || Util.isEmpty(telephone)
					|| Util.isEmpty(userName)) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}
			// 用户ID
			String userId = Util.getLoginUser(request.getSession()).getId();
			UserConf userConf = new UserConf();
			
			// 所有地址信息
			List<UserConf> confList = userManager.findUserConfList(userId);
			
			if ("save".equals(saveOrUpdate)) {
				if (Util.isEmpty(confList)) {
					if (Util.isEmpty(attribute)) {
						attribute = "address_1";
					}
					result = saveAddress(userId, address, telephone, userName,
							attribute);
				} else {
					if (confList.size() >= 10) {
						result = "4";
					} else {
						if (Util.isEmpty(attribute)) {
							attribute = addressAttrHandle(confList);
							result = saveAddress(userId, address, telephone,
									userName, attribute);
						} else {
							// 此处为之前的def地址
							userConf = userManager.findUserConfInfo(userId,
									attribute);
							if (userConf != null) {
								// 更新之前的def地址为新曾的编号
								String nextAttribute = addressAttrHandle(confList);
								userConf.setAttribute(nextAttribute);
								boolean flag = userManager
										.updateUserConf(userConf);
								if (flag) {
									result = saveAddress(userId, address,
											telephone, userName, attribute);
								} else {
									result = "2";
								}
							} else {
								result = saveAddress(userId, address,
										telephone, userName, attribute);
							}
						}
					}
				}
				response.getWriter().print(result);
				return null;
			} else if ("update".equals(saveOrUpdate)) {
				if (Util.isEmpty(attribute)) {
					// 查询信息
					userConf = userManager.findUserConfInfo(userId, oldAttribute);
					if (userConf == null) {
						result = "2";
					} else {
						if(oldAttribute.contains("def")){
							String nextAttribute = addressAttrHandle(confList);
							result = updateAddress(userConf, address, telephone,
									userName, nextAttribute);
						}else{
							result = updateAddress(userConf, address, telephone,
									userName, oldAttribute);
						}
					}
				} else {
					UserConf oldUserConf = userManager.findUserConfInfo(userId,oldAttribute);
					if(oldAttribute.contains("def")){
						if (oldUserConf != null) {
							result = updateAddress(oldUserConf, address,
									telephone, userName, attribute);
						} else {
							result = "2";
						}
					}else{
						// 查询之前是否有默认信息
						userConf = userManager.findUserConfInfo(userId, attribute);
						if (userConf == null) {
							if (oldUserConf != null) {
								result = updateAddress(oldUserConf, address,
										telephone, userName, attribute);
							} else {
								result = "2";
							}
						} else {
							// 更新之前的默认信息编号为当前信息的编号
							userConf.setAttribute(oldAttribute);
							boolean flag = userManager.updateUserConf(userConf);
							if (flag) {
								result = updateAddress(oldUserConf, address,
										telephone, userName, attribute);
							} else {
								result = "2";
							}
						}
					}
				}
			}
			response.getWriter().print(result);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			result = "2";
			response.getWriter().print(result);
			return null;
		}
	}

	private String addressAttrHandle(List<UserConf> allConfList) {
		String addressAttr = "";
		List<Integer> bianhaoList = new ArrayList<Integer>();
		for (UserConf conf : allConfList) {
			String attr = conf.getAttribute();
			if (attr.contains("address")) {
				if (!"def".equals(attr.split("_")[1])) {
					bianhaoList.add(Integer.valueOf(attr.split("_")[1]));
				}
			}
		}
		if(Util.isEmpty(bianhaoList)){
			addressAttr = "address_1";
		}else{
			Collections.sort(bianhaoList);
			int nexAttr = bianhaoList.get(bianhaoList.size() - 1) + 1;
			addressAttr = "address_" + nexAttr;
		}
		return addressAttr;
	}

	private String saveAddress(String userId, String address, String telephone,
			String userName, String attribute) {
		String result = "";
		UserConf confNew = new UserConf();
		confNew.setUserId(userId);
		confNew.setAttribute(attribute);

		StringBuilder values = new StringBuilder();
		values.append(userName).append("_receive_").append(telephone)
				.append("_receive_").append(address);
		confNew.setValue(values.toString());
		boolean saveFlag = userManager.updateUserConf(confNew);
		if (saveFlag) {
			result = "3";
		} else {
			result = "2";
		}
		return result;
	}

	private String updateAddress(UserConf updateUserConf, String address,
			String telephone, String userName, String attribute) {
		String result = "";
		updateUserConf.setAttribute(attribute);

		StringBuilder values = new StringBuilder();
		values.append(userName).append("_receive_").append(telephone)
				.append("_receive_").append(address);
		updateUserConf.setValue(values.toString());
		boolean updateFlag = userManager.updateUserConf(updateUserConf);
		if (updateFlag) {
			result = "3";
		} else {
			result = "2";
		}
		return result;
	}
}
