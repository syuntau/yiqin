package com.yiqin.shop.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yiqin.pojo.UserConf;
import com.yiqin.service.UserManager;
import com.yiqin.util.Util;

/**
 * 更新或新增发票信息
 * 
 * @author liujun
 * 
 */
public class ModifyUserInvoiceAction extends ActionSupport {

	private static final long serialVersionUID = -1346516082640756595L;

	private UserManager userManager;

	// 发票类型
	private String fapiaolx;

	// 发票抬头
	private String fapiaotaitou;

	// 发票明细
	private String fapiaomingxi;

	// 发票属性
	private String attribute;

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getFapiaolx() {
		return fapiaolx;
	}

	public void setFapiaolx(String fapiaolx) {
		this.fapiaolx = fapiaolx;
	}

	public String getFapiaotaitou() {
		return fapiaotaitou;
	}

	public void setFapiaotaitou(String fapiaotaitou) {
		this.fapiaotaitou = fapiaotaitou;
	}

	public String getFapiaomingxi() {
		return fapiaomingxi;
	}

	public void setFapiaomingxi(String fapiaomingxi) {
		this.fapiaomingxi = fapiaomingxi;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/json;charset=UTF-8");
		String result = "";
		try {
			if (Util.isEmpty(fapiaomingxi)) {
				result = "1";
				response.getWriter().print(result);
				return null;
			}
			// 用户ID
			String userId = Util.getLoginUser(request.getSession()).getId();

			// 所有发票信息
			List<UserConf> confList = userManager.findUserInvoiceList(userId);

			if (Util.isEmpty(attribute)) {
				if (Util.isEmpty(confList)) {
					attribute = "invoice_1";
					result = saveInvoice(userId, fapiaolx, fapiaotaitou,
							fapiaomingxi, attribute);
				} else {
					if (confList.size() >= 5) {
						result = "4";
					} else {
						attribute = invoiceAttrHandle(confList);
						result = saveInvoice(userId, fapiaolx, fapiaotaitou,
								fapiaomingxi, attribute);
					}
				}
			} else {
				UserConf tempUserConf = userManager.findUserConfInfo(userId,
						attribute);
				if (tempUserConf != null) {
					result = updateInvoice(tempUserConf, fapiaolx,
							fapiaotaitou, fapiaomingxi, attribute);
				} else {
					result = "2";
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

	private String invoiceAttrHandle(List<UserConf> allConfList) {
		String invoiceAttr = "";
		List<Integer> bianhaoList = new ArrayList<Integer>();
		for (UserConf conf : allConfList) {
			String attr = conf.getAttribute();
			bianhaoList.add(Integer.valueOf(attr.split("_")[1]));
		}
		if (Util.isEmpty(bianhaoList)) {
			invoiceAttr = "invoice_1";
		} else {
			Collections.sort(bianhaoList);
			int nexAttr = bianhaoList.get(bianhaoList.size() - 1) + 1;
			invoiceAttr = "invoice_" + nexAttr;
		}
		return invoiceAttr;
	}

	private String saveInvoice(String userId, String fapiaolx,
			String fapiaotaitou, String fapiaomingxi, String attribute) {
		String result = "";
		UserConf confNew = new UserConf();
		confNew.setUserId(userId);
		confNew.setAttribute(attribute);

		StringBuilder values = new StringBuilder();
		values.append(fapiaolx).append("_invoice_").append(fapiaotaitou)
				.append("_invoice_").append(fapiaomingxi);
		confNew.setValue(values.toString());
		boolean saveFlag = userManager.updateUserConf(confNew);
		if (saveFlag) {
			result = "3";
		} else {
			result = "2";
		}
		return result;
	}

	private String updateInvoice(UserConf updateUserConf, String fapiaolx,
			String fapiaotaitou, String fapiaomingxi, String attribute) {
		String result = "";
		updateUserConf.setAttribute(attribute);

		StringBuilder values = new StringBuilder();
		values.append(fapiaolx).append("_invoice_").append(fapiaotaitou)
				.append("_invoice_").append(fapiaomingxi);
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
